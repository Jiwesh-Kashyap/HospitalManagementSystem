package com.hospital.backendHospitalManagement.ui;

import javax.swing.*;
import java.awt.*;

public class DoctorPatientRecordsPage extends JPanel {
    public DoctorPatientRecordsPage(PromethiusFrame frame) {
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 60));
        
        JButton backBtn = new JButton("◀ Back");
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> frame.showPage("DOCTOR_DASHBOARD"));
        header.add(backBtn, BorderLayout.WEST);
        
        JLabel title = new JLabel("Central Patient Records Database", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        JLabel placeholder = new JLabel("📂 Securely browse and update clinical patient records.");
        placeholder.setFont(new Font("SansSerif", Font.ITALIC, 20));
        content.add(placeholder);
        add(content, BorderLayout.CENTER);
    }
}
