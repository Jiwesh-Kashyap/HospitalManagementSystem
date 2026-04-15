package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import javax.swing.*;
import java.awt.*;

public class SignupPage extends JPanel {
    private final PromethiusFrame frame;
    private final JTextField nameField, emailField, bloodGroupField, specialisationField;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleCombo;

    public SignupPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.BEIGE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        JButton backBtn = new JButton("◀ Back to Home");
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

        JLabel title = new JLabel("Create Account - Promethius+");
        title.setFont(PromethiusFrame.HEADER_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(title);
        form.add(Box.createVerticalStrut(20));

        form.add(createLabel("Full Name:"));
        nameField = new JTextField();
        form.add(nameField);
        form.add(Box.createVerticalStrut(10));

        form.add(createLabel("Email Address:"));
        emailField = new JTextField();
        form.add(emailField);
        form.add(Box.createVerticalStrut(10));

        form.add(createLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);
        form.add(Box.createVerticalStrut(10));

        form.add(createLabel("Role:"));
        roleCombo = new JComboBox<>(new String[]{"PATIENT", "DOCTOR"});
        form.add(roleCombo);
        form.add(Box.createVerticalStrut(10));

        form.add(createLabel("Blood Group:"));
        bloodGroupField = new JTextField();
        form.add(bloodGroupField);
        form.add(Box.createVerticalStrut(10));

        form.add(createLabel("Specialisation (For Doctors):"));
        specialisationField = new JTextField();
        form.add(specialisationField);
        form.add(Box.createVerticalStrut(20));

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupBtn.setPreferredSize(new Dimension(200, 50));
        signupBtn.addActionListener(e -> handleSignup());
        form.add(signupBtn);

        JButton loginLink = new JButton("Already have an account? Log In");
        loginLink.setContentAreaFilled(false);
        loginLink.setBorderPainted(false);
        loginLink.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        loginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLink.addActionListener(e -> frame.showPage("LOGIN"));
        form.add(Box.createVerticalStrut(10));
        form.add(loginLink);

        container.add(form);
        add(container, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(PromethiusFrame.MAIN_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void handleSignup() {
        String name = nameField.getText();
        String email = emailField.getText();
        String role = (String) roleCombo.getSelectedItem();
        
        if (email.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Account created successfully for " + name + "! (Demo Mode)");
        
        // Update session state in frame
        frame.setLoggedIn(email, role, name);
        
        // Redirect to Landing instead of Dashboard
        frame.showPage("LANDING");
    }
}
