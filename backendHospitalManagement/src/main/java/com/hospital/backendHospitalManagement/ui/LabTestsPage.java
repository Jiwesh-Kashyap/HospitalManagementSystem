package com.hospital.backendHospitalManagement.ui;

import javax.swing.*;
import java.awt.*;

public class LabTestsPage extends JPanel {
    private final PromethiusFrame frame;

    public LabTestsPage(PromethiusFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 70));

        JButton backBtn = new JButton("◀ Home");
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        backBtn.addActionListener(e -> frame.showPage("LANDING"));
        header.add(backBtn, BorderLayout.WEST);

        JLabel title = new JLabel("Diagnostic & Lab Tests", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(PromethiusFrame.PURE_WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        content.add(createLabTestRow("Full Body Checkup", "60+ Parameters included", "24 Hours", "₹1,999", "🔬"));
        content.add(Box.createVerticalStrut(20));
        content.add(createLabTestRow("Blood Glucose Test", "Fast and accurate", "2 Hours", "₹150", "🩸"));
        content.add(Box.createVerticalStrut(20));
        content.add(createLabTestRow("MRI Scan - Brain", "High resolution imaging", "12 Hours", "₹4,500", "🧠"));
        content.add(Box.createVerticalStrut(20));
        content.add(createLabTestRow("X-Ray - Chest", "Standard diagnostic", "1 Hour", "₹500", "🩻"));

        add(new JScrollPane(content), BorderLayout.CENTER);
    }

    private JPanel createLabTestRow(String name, String desc, String time, String price, String icon) {
        JPanel card = new JPanel(new BorderLayout(25, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        card.setMaximumSize(new Dimension(1000, 130));

        // Icon
        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 50));
        card.add(iconLbl, BorderLayout.WEST);

        // Info
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        
        JLabel n = new JLabel(name);
        n.setFont(new Font("SansSerif", Font.BOLD, 22));
        n.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        
        JLabel d = new JLabel(desc);
        d.setFont(new Font("SansSerif", Font.PLAIN, 16));
        d.setForeground(Color.GRAY);
        
        JLabel t = new JLabel("⏱ Result in: " + time);
        t.setFont(new Font("SansSerif", Font.BOLD, 14));
        t.setForeground(PromethiusFrame.CYAN);
        
        info.add(Box.createVerticalGlue());
        info.add(n);
        info.add(d);
        info.add(t);
        info.add(Box.createVerticalGlue());
        card.add(info, BorderLayout.CENTER);

        // Price & Action
        JPanel action = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 25));
        action.setOpaque(false);
        
        JLabel p = new JLabel(price);
        p.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        JButton book = new JButton("Book Test");
        book.setBackground(PromethiusFrame.APOLLO_BLUE);
        book.setForeground(Color.WHITE);
        book.setFont(new Font("SansSerif", Font.BOLD, 16));
        book.setPreferredSize(new Dimension(140, 45));
        
        action.add(p);
        action.add(book);
        card.add(action, BorderLayout.EAST);

        return card;
    }
}
