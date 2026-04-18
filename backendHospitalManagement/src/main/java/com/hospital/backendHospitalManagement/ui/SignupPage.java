package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hospital.backendHospitalManagement.model.*;

import javax.swing.*;
import java.awt.*;

public class SignupPage extends JPanel {
    private final PromethiusFrame frame;
    private final ApplicationContext context;
    private final JTextField nameField, emailField, bloodGroupField, specialisationField;
    private final JLabel bloodLabel, specLabel;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleCombo;

    public SignupPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        this.context = context;
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

        bloodLabel = createLabel("Blood Group:");
        form.add(bloodLabel);
        bloodGroupField = new JTextField();
        form.add(bloodGroupField);
        form.add(Box.createVerticalStrut(10));

        specLabel = createLabel("Specialisation (For Doctors):");
        form.add(specLabel);
        specialisationField = new JTextField();
        form.add(specialisationField);
        form.add(Box.createVerticalStrut(20));

        roleCombo.addActionListener(e -> updateFieldVisibility());
        updateFieldVisibility(); // Initial trigger

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

    private void updateFieldVisibility() {
        String role = (String) roleCombo.getSelectedItem();
        boolean isDoctor = "DOCTOR".equals(role);
        
        // Hide blood group for both as per user preference (or at least for patients)
        // User said: "if i sign up as patient i should not need to fill in blood type"
        // And "if we select patient we need not to fill in the department its useless"
        
        bloodLabel.setVisible(false);
        bloodGroupField.setVisible(false);
        
        specLabel.setVisible(isDoctor);
        specialisationField.setVisible(isDoctor);
        
        revalidate();
        repaint();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(PromethiusFrame.MAIN_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void handleSignup() {
        try {
            String role = (String) roleCombo.getSelectedItem();
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String bg = bloodGroupField.getText();
            String spec = specialisationField.getText();

            PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
            String encodedHash = encoder.encode(password);

            Long newId = null;
            if ("DOCTOR".equals(role)) {
                Doctor newDoc = new Doctor();
                newDoc.setName(name);
                newDoc.setEmail(email);
                newDoc.setPassword(encodedHash);
                newDoc.setRole("doctor");
                newDoc.setSpecialisation(spec);

                DoctorRepo drRepo = context.getBean(DoctorRepo.class);
                newDoc = drRepo.save(newDoc);
                newId = newDoc.getId();

            } else {
                Patient newPatient = new Patient();
                newPatient.setName(name);
                newPatient.setEmail(email);
                newPatient.setPassword(encodedHash);
                newPatient.setRole("patient");
                newPatient.setBloodGroup(bg);

                PatientRepo prRepo = context.getBean(PatientRepo.class);
                newPatient = prRepo.save(newPatient);
                newId = newPatient.getId();
            }

            frame.setLoggedInUserId(newId);
            JOptionPane.showMessageDialog(this, "Account created successfully!");

            if ("DOCTOR".equals(role)) frame.showPage("DOCTOR_DASHBOARD");
            else frame.showPage("PATIENT_DASHBOARD");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating account: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
