package com.hospital.backendHospitalManagement.ui;

import javax.swing.*;
import java.awt.*; 

public class DoctorRoundsPage extends JPanel {
    public DoctorRoundsPage(PromethiusFrame frame) {
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
        
        JLabel title = new JLabel("Daily Hospital Rounds", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setOpaque(false);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Info Banner
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(240, 250, 255));
        banner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PromethiusFrame.CYAN, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel bannerTxt = new JLabel("📅 Physical Rounds Schedule: April 15, 2026 (Morning Shift)");
        bannerTxt.setFont(new Font("SansSerif", Font.BOLD, 18));
        bannerTxt.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        banner.add(bannerTxt, BorderLayout.CENTER);
        
        mainContainer.add(banner, BorderLayout.NORTH);

        // Rounds Table
        String[] columnNames = {"Time Slot", "Ward / Room", "Patient Name", "Condition Status", "Priority"};
        Object[][] data = {
            {"08:30 AM", "ICU - Bed 4", "Sandra Bullock", "Critical but Stable", "URGENT"},
            {"09:15 AM", "General - 402", "John Doe", "Improving", "NORMAL"},
            {"10:00 AM", "Post-Op - 212", "Jane Smith", "Post-Surgical Review", "HIGH"},
            {"10:45 AM", "General - 405", "Michael Brown", "Observation", "NORMAL"},
            {"11:30 AM", "Ortho - 301", "Brad Pitt", "Stable Recovery", "NORMAL"},
            {"12:15 PM", "Ortho - 303", "Tom Cruise", "Pain Management", "HIGH"}
        };

        JTable table = new JTable(data, columnNames);
        table.setFont(PromethiusFrame.MAIN_FONT);
        table.setRowHeight(50);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        mainContainer.add(scrollPane, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);
    }
}
