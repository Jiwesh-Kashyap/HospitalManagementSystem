package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hospital.backendHospitalManagement.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginPage extends JPanel {
    private final PromethiusFrame frame;
    private final ApplicationContext context;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleCombo;

    public LoginPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        this.context = context;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.BEIGE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        JButton backBtn = new JButton("<- Back to Home");
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> frame.showPage("LANDING"));
        header.add(backBtn, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

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
        
        JLabel subtitle = new JLabel("Welcome back to your healthcare portal", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(Box.createVerticalStrut(5));
        form.add(subtitle);
        
        form.add(Box.createVerticalStrut(25));

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

        JButton loginBtn = new JButton("Login to Dashboard");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setPreferredSize(new Dimension(250, 45));
        loginBtn.setMaximumSize(new Dimension(250, 45));
        loginBtn.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
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
        String role = (String) roleCombo.getSelectedItem();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        try {
            PersonRepo personRepo = context.getBean(PersonRepo.class);
            PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

            Optional<Person> personOpt = personRepo.findByEmail(email);

            if (personOpt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid credentials: User not found.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Person person = personOpt.get();

            boolean matches = false;
            if (passwordEncoder.matches(password, person.getPassword())) {
                matches = true;
            } else if (password.equals(person.getPassword())) {
                matches = true;
            }

            if (!matches) {
                JOptionPane.showMessageDialog(this, "Invalid credentials: Password incorrect.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (("Doctor".equals(role) && !person.getRole().equalsIgnoreCase("doctor")) ||
                ("Patient".equals(role) && !person.getRole().equalsIgnoreCase("patient"))) {
                JOptionPane.showMessageDialog(this, "Invalid role selected for this account.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            frame.setLoggedInUserId(person.getId());
            frame.setLoggedIn(person.getEmail(), role, person.getName());

            JOptionPane.showMessageDialog(this, "Logged in as " + role + " successfully!");

            if ("Doctor".equals(role)) {
                frame.showPage("DOCTOR_DASHBOARD");
            } else {
                frame.showPage("PATIENT_DASHBOARD");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not connect to database for login.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
