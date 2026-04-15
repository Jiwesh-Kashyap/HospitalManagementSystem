package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {
    private final PromethiusFrame frame;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleCombo;

    public LoginPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.BEIGE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        JButton backBtn = new JButton("<- Back to Home");
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> frame.showPage("LANDING"));
        header.add(backBtn, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Form
        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);
        
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PromethiusFrame.CYAN, 2),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel title = new JLabel("Log In to Promethius+");
        title.setFont(PromethiusFrame.HEADER_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(title);
        form.add(Box.createVerticalStrut(20));

        form.add(createLabel("Login As:"));
        roleCombo = new JComboBox<>(new String[]{"Patient", "Doctor"});
        roleCombo.setMaximumSize(new Dimension(400, 40));
        form.add(roleCombo);
        form.add(Box.createVerticalStrut(10));

        form.add(createLabel("Email Address:"));
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(400, 40));
        form.add(emailField);
        form.add(Box.createVerticalStrut(10));

        form.add(createLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(400, 40));
        form.add(passwordField);
        form.add(Box.createVerticalStrut(20));

        JButton loginBtn = new JButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setPreferredSize(new Dimension(200, 50));
        loginBtn.addActionListener(e -> handleLogin());
        form.add(loginBtn);

        JButton signupLink = new JButton("Don't have an account? Sign Up");
        signupLink.setContentAreaFilled(false);
        signupLink.setBorderPainted(false);
        signupLink.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        signupLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupLink.addActionListener(e -> frame.showPage("SIGNUP"));
        form.add(Box.createVerticalStrut(10));
        form.add(signupLink);

        container.add(form);
        add(container, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(PromethiusFrame.MAIN_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void handleLogin() {
        String email = emailField.getText();
        String role = (String) roleCombo.getSelectedItem();
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Logged in as " + role + " successfully! (Demo Mode)");
        
        // Extract a name from email if name isn't available
        String name = email.split("@")[0];
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        // Update session state in frame
        frame.setLoggedIn(email, role.toUpperCase(), name);
        
        // Redirect to Landing instead of Dashboard
        frame.showPage("LANDING");
    }
}
