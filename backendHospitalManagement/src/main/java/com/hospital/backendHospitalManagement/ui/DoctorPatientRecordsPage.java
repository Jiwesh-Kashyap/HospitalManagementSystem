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
        
        JLabel title = new JLabel("Central Patient Records Database", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setOpaque(false);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Search Bar Section
        JPanel searchSection = new JPanel(new BorderLayout(15, 0));
        searchSection.setOpaque(false);
        searchSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel searchLbl = new JLabel("🔍 Search Patient History:");
        searchLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        searchSection.add(searchLbl, BorderLayout.WEST);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400, 45));
        searchSection.add(searchField, BorderLayout.CENTER);
        
        JButton filterBtn = new JButton("Filter Records");
        filterBtn.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        filterBtn.setForeground(Color.WHITE);
        searchSection.add(filterBtn, BorderLayout.EAST);
        
        mainContainer.add(searchSection, BorderLayout.NORTH);

        // Patient History Table
        String[] columnNames = {"UID", "Patient Name", "Age/Gender", "Diagnosis", "Chronic Conditions", "Last Visit"};
        Object[][] data = {
            {"P-9921", "John Doe", "45M", "Hypertension", "Type 2 Diabetes", "2026-04-14"},
            {"P-8812", "Jane Smith", "32F", "Post-Op Recovery", "None", "2026-04-14"},
            {"P-7723", "Michael Brown", "60M", "Osteoarthritis", "Heart Disease", "2026-04-13"},
            {"P-6634", "Emily Davis", "28F", "Seasonal Allergies", "Asthma", "2026-04-13"},
            {"P-5545", "Sandra Bullock", "50F", "Acute Gastritis", "Migraine", "2026-04-12"},
            {"P-4456", "Brad Pitt", "58M", "Fractured Fibula", "None", "2026-04-11"},
            {"P-3367", "Tom Cruise", "61M", "ACL Tear", "None", "2026-04-10"}
        };

        JTable table = new JTable(data, columnNames);
        table.setFont(PromethiusFrame.MAIN_FONT);
        table.setRowHeight(45);
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
