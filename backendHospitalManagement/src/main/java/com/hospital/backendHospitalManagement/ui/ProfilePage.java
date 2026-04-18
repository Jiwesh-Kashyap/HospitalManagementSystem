package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;
import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JPanel {
    private final PromethiusFrame frame;
    private final JLabel nameVal, emailVal, roleVal;

    public ProfilePage(PromethiusFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.BEIGE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 60));
        
        JButton backBtn = new JButton("◀ Back to Dashboard");
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> {
            if (frame.isDoctorLoggedIn()) frame.showPage("DOCTOR_DASHBOARD");
            else frame.showPage("PATIENT_DASHBOARD");
        });
        header.add(backBtn, BorderLayout.WEST);
        
        JLabel title = new JLabel("My Profile & Security", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PromethiusFrame.CYAN, 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(createBoldLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        nameVal = new JLabel("-");
        form.add(nameVal, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(createBoldLabel("Email Address:"), gbc);
        gbc.gridx = 1;
        emailVal = new JLabel("-");
        form.add(emailVal, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(createBoldLabel("Account Role:"), gbc);
        gbc.gridx = 1;
        roleVal = new JLabel("-");
        form.add(roleVal, gbc);

        content.add(form);
        content.add(Box.createVerticalGlue());
        add(content, BorderLayout.CENTER);
    }

    private JLabel createBoldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 18));
        return l;
    }

    public void refreshData(ApplicationContext context) {
        nameVal.setText(frame.getCurrentUserName() != null ? frame.getCurrentUserName() : "N/A");
        emailVal.setText(frame.getCurrentUserEmail() != null ? frame.getCurrentUserEmail() : "N/A");
        roleVal.setText(frame.getCurrentUserRole() != null ? frame.getCurrentUserRole() : "N/A");
    }
}
