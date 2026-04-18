package com.hospital.backendHospitalManagement.ui;

import javax.swing.*;
import java.awt.*;

public class DoctorLabResultsPage extends JPanel {
    public DoctorLabResultsPage(PromethiusFrame frame) {
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 60));
        
        JPanel navBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        navBox.setOpaque(false);

        JButton homeBtn = new JButton("◀ Home");
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        homeBtn.setContentAreaFilled(false);
        homeBtn.addActionListener(e -> frame.showPage("LANDING"));
        navBox.add(homeBtn);

        JButton dashBtn = new JButton("◀ Dashboard");
        dashBtn.setForeground(Color.WHITE);
        dashBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        dashBtn.setContentAreaFilled(false);
        dashBtn.addActionListener(e -> frame.showPage("DOCTOR_DASHBOARD"));
        navBox.add(dashBtn); 

        header.add(navBox, BorderLayout.WEST);
        
        JLabel title = new JLabel("Pending Lab & Radiology Results", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        JLabel placeholder = new JLabel("🔬 Review and validate patient laboratory findings.");
        placeholder.setFont(new Font("SansSerif", Font.ITALIC, 20));
        content.add(placeholder);
        add(content, BorderLayout.CENTER);
    }
}
