package com.hospital.backendHospitalManagement.ui;

import javax.swing.*;
import java.awt.*;

public class InsurancePage extends JPanel {
    private final PromethiusFrame frame;

    public InsurancePage(PromethiusFrame frame) {
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

        JLabel title = new JLabel("Health Insurance Plans", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel(new GridLayout(1, 3, 30, 0));
        content.setBackground(PromethiusFrame.PURE_WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

        content.add(createPolicyCard("Diamond Plan", "Premium Coverage", "₹2,500/mo", new String[]{"Unlimited Doctor Consults", "Free Hospital Stays", "24/7 Support", "Global Coverage"}, PromethiusFrame.CYAN));
        content.add(createPolicyCard("Platinum Plan", "Essential Coverage", "₹1,200/mo", new String[]{"10 Free Consults/Yr", "50% Hospital Discount", "Dedicated Helpline", "India Coverage"}, PromethiusFrame.STAR_COMMAND_BLUE));
        content.add(createPolicyCard("Gold Plan", "Basic Coverage", "₹600/mo", new String[]{"3 Free Consults/Yr", "20% Hospital Discount", "Email Support", "State Coverage"}, Color.GRAY));

        add(new JScrollPane(content), BorderLayout.CENTER);
    }

    private JPanel createPolicyCard(String name, String sub, String price, String[] features, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PromethiusFrame.PURE_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        JLabel n = new JLabel(name);
        n.setFont(new Font("SansSerif", Font.BOLD, 26));
        n.setForeground(color);
        n.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel s = new JLabel(sub);
        s.setFont(new Font("SansSerif", Font.PLAIN, 16));
        s.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel p = new JLabel(price);
        p.setFont(new Font("SansSerif", Font.BOLD, 32));
        p.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(n);
        card.add(s);
        card.add(p);
        card.add(new JSeparator());
        card.add(Box.createVerticalStrut(20));

        for (String f : features) {
            JLabel fl = new JLabel("✓ " + f);
            fl.setFont(new Font("SansSerif", Font.PLAIN, 16));
            fl.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            fl.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(fl);
        }

        card.add(Box.createVerticalGlue());
        JButton select = new JButton("Select Plan");
        select.setBackground(color);
        select.setForeground(Color.WHITE);
        select.setFont(new Font("SansSerif", Font.BOLD, 18));
        select.setPreferredSize(new Dimension(200, 50));
        select.setMaximumSize(new Dimension(200, 50));
        select.setAlignmentX(Component.CENTER_ALIGNMENT);
        select.addActionListener(e -> JOptionPane.showMessageDialog(this, "Redirecting to Payment Gateway for " + name));
        card.add(select);

        return card;
    }
}
