package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientDashboard extends JPanel {
    private final PromethiusFrame frame;

    public PatientDashboard(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.BEIGE);

        // --- Sidebar ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        sidebar.setPreferredSize(new Dimension(260, 800));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        sidebar.add(createSidebarHeader("MAIN MENU"));
        sidebar.add(createSidebarNavButton("Dashboard", "PATIENT_DASHBOARD"));
        sidebar.add(createSidebarNavButton("My Appointments", "PATIENT_APPOINTMENTS"));
        sidebar.add(createSidebarNavButton("Medical Records", "PATIENT_RECORDS"));
        sidebar.add(createSidebarNavButton("Billing Summary", "PATIENT_BILLING"));
        
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(createSidebarHeader("ACCOUNT & SUPPORT"));
        sidebar.add(createSidebarNavButton("My Profile & Details", "PATIENT_PROFILE"));
        sidebar.add(createSidebarNavButton("Contact Us / Help", "PATIENT_SUPPORT"));
        sidebar.add(createSidebarNavButton("FAQs", "PATIENT_SUPPORT"));

        sidebar.add(Box.createVerticalGlue());
        JButton logout = createSidebarButton("Logout");
        logout.setForeground(new Color(255, 100, 100));
        logout.addActionListener(e -> frame.showPage("LANDING"));
        sidebar.add(logout);
        add(sidebar, BorderLayout.WEST);

        // --- Main Content Area ---
        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setOpaque(false);
        mainArea.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Refactored Header: Welcome (Left) + Book Button (Right)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel welcome = new JLabel("Welcome Back, Patient!");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 30));
        welcome.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        headerPanel.add(welcome, BorderLayout.WEST);

        // Smaller Booking Button at Top-Right
        JButton topBookBtn = new JButton("Book Appointment");
        topBookBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        topBookBtn.setBackground(PromethiusFrame.CYAN);
        topBookBtn.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        topBookBtn.setPreferredSize(new Dimension(180, 40));
        topBookBtn.setFocusPainted(false);
        topBookBtn.addActionListener(e -> frame.showPage("PATIENT_BOOKING_LIST"));
        headerPanel.add(topBookBtn, BorderLayout.EAST);

        mainArea.add(headerPanel, BorderLayout.NORTH);

        // Central Scroll Pane for content
        JPanel dashboardContent = new JPanel();
        dashboardContent.setLayout(new BoxLayout(dashboardContent, BoxLayout.Y_AXIS));
        dashboardContent.setOpaque(false);

        // 1. Horizontal Stats Row
        JPanel statRow = new JPanel(new GridLayout(1, 4, 15, 0));
        statRow.setOpaque(false);
        statRow.setMaximumSize(new Dimension(1400, 100));
        statRow.add(createCompactStatCard("Appts", "2"));
        statRow.add(createCompactStatCard("Reports", "5"));
        statRow.add(createCompactStatCard("Health", "85%"));
        statRow.add(createCompactStatCard("Vitals", "120/80"));
        
        dashboardContent.add(Box.createVerticalStrut(20));
        dashboardContent.add(statRow);
        dashboardContent.add(Box.createVerticalStrut(30));

        // 2. Dashboard Sections (Quick Previews)
        dashboardContent.add(createDataSection("Upcoming Appointments", new String[]{"Date", "Doctor", "Reason"}, new Object[][]{
            {"Apr 15, 10:00 AM", "Dr. Chaithra H", "Internal Medicine"},
            {"Apr 22, 02:30 PM", "Dr. Summaiya B", "General Checkup"}
        }));
        
        dashboardContent.add(Box.createVerticalStrut(25));
        dashboardContent.add(createDataSection("Recent Medical Records", new String[]{"Document Name", "Date", "Category"}, new Object[][]{
            {"Blood_Report_Apr.pdf", "Apr 10, 2026", "Lab Test"},
            {"X-Ray_Chest_Digital.jpg", "Mar 28, 2026", "Radiology"}
        }));

        dashboardContent.add(Box.createVerticalStrut(25));
        dashboardContent.add(createDataSection("Recent Billing", new String[]{"Invoice #", "Service", "Amount"}, new Object[][]{
            {"INV-2026-001", "Consultation Fee", "₹699"},
            {"INV-2026-002", "Pharmacy - Meds", "₹1,240"}
        }));

        JScrollPane scrollPane = new JScrollPane(dashboardContent);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        mainArea.add(scrollPane, BorderLayout.CENTER);

        add(mainArea, BorderLayout.CENTER);
    }

    private JButton createSidebarNavButton(String text, String pageName) {
        JButton btn = createSidebarButton(text);
        btn.addActionListener(e -> frame.showPage(pageName));
        return btn;
    }

    private JLabel createSidebarHeader(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(200, 230, 255));
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(260, 45));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 17));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return btn;
    }

    private JPanel createCompactStatCard(String title, String val) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PromethiusFrame.CYAN, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 13));
        t.setForeground(Color.GRAY);
        card.add(t, BorderLayout.NORTH);

        JLabel v = new JLabel(val);
        v.setFont(new Font("SansSerif", Font.BOLD, 22));
        v.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        v.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(v, BorderLayout.CENTER);

        return card;
    }

    private JPanel createDataSection(String title, String[] headers, Object[][] data) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        lbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
        section.add(lbl, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(data, headers);
        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        table.setFillsViewportHeight(true);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(800, 140));
        section.add(scroll, BorderLayout.CENTER);

        return section;
    }
}
