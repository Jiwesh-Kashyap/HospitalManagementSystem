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

        // --- Sidebar Redesign (Utilizing more space) ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        sidebar.setPreferredSize(new Dimension(320, 800)); // Increased width
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20)); // Increased padding

        sidebar.add(createSidebarHeader("MAIN MENU"));
        sidebar.add(createSidebarNavButton("Dashboard", "PATIENT_DASHBOARD"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarNavButton("My Appointments", "PATIENT_APPOINTMENTS"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarNavButton("Medical Records", "PATIENT_RECORDS"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarNavButton("Billing Summary", "PATIENT_BILLING"));
        
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(createSidebarHeader("ACCOUNT & SUPPORT"));
        sidebar.add(createSidebarNavButton("My Profile & Details", "PATIENT_PROFILE"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarNavButton("Contact Us / Help", "PATIENT_SUPPORT"));
        sidebar.add(Box.createVerticalStrut(10));
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
        
        JPanel titleBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        titleBox.setOpaque(false);

        JButton homeBtn = new JButton("◀ Home");
        homeBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        homeBtn.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        homeBtn.setContentAreaFilled(false);
        homeBtn.addActionListener(e -> frame.showPage("LANDING"));
        titleBox.add(homeBtn);

        JLabel welcome = new JLabel("Welcome Back, Patient!");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 30));
        welcome.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        titleBox.add(welcome);

        headerPanel.add(titleBox, BorderLayout.WEST);

        // Stylized Booking Button (Prominent Design)
        JButton topBookBtn = new JButton("📅 Book New Appointment");
        topBookBtn.setFont(new Font("SansSerif", Font.BOLD, 17));
        topBookBtn.setBackground(PromethiusFrame.CYAN);
        topBookBtn.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        topBookBtn.setPreferredSize(new Dimension(260, 50));
        topBookBtn.setFocusPainted(false);
        topBookBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        topBookBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PromethiusFrame.STAR_COMMAND_BLUE, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
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
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(320, 50));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setAutoCreateRowSorter(true); // Enable Sorting
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scroll.setPreferredSize(new Dimension(800, 160));
        section.add(scroll, BorderLayout.CENTER);

        return section;
    }
}
