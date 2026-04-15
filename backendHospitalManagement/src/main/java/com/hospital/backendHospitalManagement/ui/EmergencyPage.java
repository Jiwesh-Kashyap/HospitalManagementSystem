package com.hospital.backendHospitalManagement.ui;

import javax.swing.*;
import java.awt.*;

public class EmergencyPage extends JPanel {
    private final PromethiusFrame frame;

    public EmergencyPage(PromethiusFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(200, 0, 0)); // Red for Emergency
        header.setPreferredSize(new Dimension(1200, 70));

        JButton backBtn = new JButton("◀ Home");
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        backBtn.addActionListener(e -> frame.showPage("LANDING"));
        header.add(backBtn, BorderLayout.WEST);

        JLabel title = new JLabel("EMERGENCY RESPONSE", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(PromethiusFrame.PURE_WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Emergency Button
        JButton callBtn = new JButton("🚑 CALL EMERGENCY NOW");
        callBtn.setFont(new Font("SansSerif", Font.BOLD, 36));
        callBtn.setBackground(new Color(220, 0, 0));
        callBtn.setForeground(Color.WHITE);
        callBtn.setPreferredSize(new Dimension(600, 150));
        callBtn.setFocusPainted(false);
        callBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Calling Emergency Services... 102 / 911"));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(callBtn, gbc);

        // Info Panel
        JPanel info = new JPanel(new GridLayout(4, 1, 10, 10));
        info.setOpaque(false);
        info.add(createEmergencyContact("Ambulance Service", "102 / 108"));
        info.add(createEmergencyContact("Police Department", "100"));
        info.add(createEmergencyContact("Fire Department", "101"));
        info.add(createEmergencyContact("Hospital Front Desk", "+91 98765 43210"));
        
        gbc.gridy = 1;
        content.add(info, gbc);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createEmergencyContact(String label, String number) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(255, 240, 240));
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 20));
        l.setForeground(new Color(150, 0, 0));
        
        JLabel n = new JLabel(number);
        n.setFont(new Font("SansSerif", Font.BOLD, 24));
        n.setForeground(Color.BLACK);
        
        p.add(l, BorderLayout.WEST);
        p.add(n, BorderLayout.EAST);
        
        return p;
    }
}
