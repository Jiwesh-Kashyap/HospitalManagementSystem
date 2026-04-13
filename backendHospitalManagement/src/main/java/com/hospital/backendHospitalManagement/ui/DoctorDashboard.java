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

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        sidebar.setPreferredSize(new Dimension(250, 800));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        sidebar.add(createSidebarHeader("DOCTOR MENU"));
        sidebar.add(createSidebarNavButton("Physician Portal", "DOCTOR_DASHBOARD"));
        sidebar.add(createSidebarNavButton("Upcoming Rounds", "DOCTOR_ROUNDS"));
        sidebar.add(createSidebarNavButton("Patient Records", "DOCTOR_PATIENT_RECORDS"));
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
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Physician Portal - Welcome Dr. Smith");
        title.setFont(PromethiusFrame.HEADER_FONT);
        title.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        content.add(title, BorderLayout.NORTH);

        // Quick Stats
        JPanel stats = new JPanel(new GridLayout(1, 3, 20, 0));
        stats.setOpaque(false);
        stats.setPreferredSize(new Dimension(800, 80));
        stats.add(createCompactStatCard("Today's Appts", "12"));
        stats.add(createCompactStatCard("Pending Rounds", "4"));
        stats.add(createCompactStatCard("Critical Alerts", "1"));
        
        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setOpaque(false);
        topArea.add(title, BorderLayout.NORTH);
        topArea.add(stats, BorderLayout.SOUTH);
        content.add(topArea, BorderLayout.NORTH);

        // Mock Patient Table
        String[] columnNames = {"Time", "Patient Name", "Reason", "Status"};
        Object[][] data = {
            {"09:00 AM", "John Doe", "Routine Checkup", "Waiting"},
            {"10:30 AM", "Jane Smith", "Post-Op Review", "Confirmed"},
            {"11:00 AM", "Michael Brown", "Consultation", "Tentative"},
            {"12:00 PM", "Emily Davis", "Prescription Renewal", "Waiting"},
            {"02:00 PM", "Sandra Bullock", "Emergency Follow-up", "Arrived"}
        };
        JTable table = new JTable(data, columnNames);
        table.setFont(PromethiusFrame.MAIN_FONT);
        table.setRowHeight(45);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        table.getTableHeader().setBackground(PromethiusFrame.CYAN);
        table.getTableHeader().setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PromethiusFrame.CYAN, 1));
        content.add(scrollPane, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }

    private JLabel createSidebarHeader(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(200, 230, 255));
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JButton createSidebarNavButton(String text, String pageName) {
        JButton btn = createSidebarButton(text);
        btn.addActionListener(e -> frame.showPage(pageName));
        return btn;
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(250, 45));
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
}
