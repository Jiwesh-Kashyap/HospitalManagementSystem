package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import javax.swing.*;
import java.awt.*;

public class DoctorDashboard extends JPanel {
    private final PromethiusFrame frame;

    public DoctorDashboard(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.BEIGE);

        // --- Sidebar Redesign (Consistency with Patient UI) ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        sidebar.setPreferredSize(new Dimension(320, 800)); // Increased width
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20)); // Generous padding

        sidebar.add(createSidebarHeader("DOCTOR MENU"));
        sidebar.add(createSidebarNavButton("Physician Portal", "DOCTOR_DASHBOARD"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarNavButton("Upcoming Rounds", "DOCTOR_ROUNDS"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarNavButton("Patient Records", "DOCTOR_PATIENT_RECORDS"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarNavButton("Lab Results", "DOCTOR_LAB_RESULTS"));
        
        sidebar.add(Box.createVerticalGlue());
        JButton logout = createSidebarButton("Logout");
        logout.setForeground(new Color(255, 100, 100));
        logout.addActionListener(e -> frame.showPage("LANDING"));
        sidebar.add(logout);
        add(sidebar, BorderLayout.WEST);

        // Main Content
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        // Header: Personalized Greeting + Home Button
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

        String docName = frame.getCurrentUserName() != null ? frame.getCurrentUserName() : "Physician";
        JLabel title = new JLabel("Welcome, Dr. " + docName);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        titleBox.add(title);
        
        headerPanel.add(titleBox, BorderLayout.WEST);
        content.add(headerPanel, BorderLayout.NORTH);

        // Quick Stats
        JPanel stats = new JPanel(new GridLayout(1, 3, 20, 0));
        stats.setOpaque(false);
        stats.setPreferredSize(new Dimension(800, 80));
        stats.add(createCompactStatCard("Today's Appts", "12"));
        stats.add(createCompactStatCard("Pending Rounds", "4"));
        stats.add(createCompactStatCard("Critical Alerts", "1"));
        
        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setOpaque(false);
        topArea.add(headerPanel, BorderLayout.NORTH);
        topArea.add(Box.createVerticalStrut(25), BorderLayout.CENTER);
        topArea.add(stats, BorderLayout.SOUTH);
        content.add(topArea, BorderLayout.NORTH);

        // --- Dashboard Content (Scrollable) ---
        JPanel dashboardContent = new JPanel();
        dashboardContent.setLayout(new BoxLayout(dashboardContent, BoxLayout.Y_AXIS));
        dashboardContent.setOpaque(false);

        // Section 1: Upcoming Appointments
        dashboardContent.add(createDataTableSection("Today's Upcoming Appointments", new String[]{"Time", "Patient Name", "Reason", "Status"}, new Object[][]{
            {"02:00 PM", "Sandra Bullock", "Emergency Follow-up", "Arrived"},
            {"03:30 PM", "Tom Cruise", "Orthopedic Review", "Confirmed"},
            {"04:15 PM", "Brad Pitt", "Routine Checkup", "Waiting"}
        }));

        dashboardContent.add(Box.createVerticalStrut(30));

        // Section 2: Recent Patient Activity (Past Appointments)
        dashboardContent.add(createDataTableSection("Recent Patient Activity (Past Appointments)", new String[]{"Date", "Patient Name", "Diagnosis", "Notes"}, new Object[][]{
            {"Apr 14, 2026", "John Doe", "Hypertension", "Prescribed Amlodipine"},
            {"Apr 14, 2026", "Jane Smith", "Post-Op Recovery", "Stable, continue rehab"},
            {"Apr 13, 2026", "Michael Brown", "Type 2 Diabetes", "HbA1c normal"},
            {"Apr 13, 2026", "Emily Davis", "Seasonal Allergies", "Prescribed Cetirizine"}
        }));

        JScrollPane scrollPane = new JScrollPane(dashboardContent);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        content.add(scrollPane, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createDataTableSection(String title, String[] headers, Object[][] data) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);
        section.setMaximumSize(new Dimension(1400, 300));
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
        section.add(lbl, BorderLayout.NORTH);

        JTable table = new JTable(data, headers);
        table.setFont(PromethiusFrame.MAIN_FONT);
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setAutoCreateRowSorter(true);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scroll.setPreferredSize(new Dimension(800, 200));
        section.add(scroll, BorderLayout.CENTER);

        return section;
    }

    private JLabel createSidebarHeader(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(200, 230, 255));
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JButton createSidebarNavButton(String text, String pageName) {
        JButton btn = createSidebarButton(text);
        btn.addActionListener(e -> frame.showPage(pageName));
        return btn;
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
}
