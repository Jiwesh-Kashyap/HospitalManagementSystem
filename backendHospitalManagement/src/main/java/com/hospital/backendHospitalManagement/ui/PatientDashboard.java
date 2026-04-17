package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientDashboard extends JPanel {
    private final PromethiusFrame frame;
    private DefaultTableModel appointmentTableModel;
    private DefaultTableModel billingTableModel;
    private DefaultTableModel recordsTableModel;
    private JLabel apptsLbl;
    private JLabel reportsLbl;
    private JLabel healthLbl;
    private JLabel vitalsLbl;

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
        logout.addActionListener(e -> frame.logout());
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
        apptsLbl = new JLabel("-");
        reportsLbl = new JLabel("-");
        healthLbl = new JLabel("-");
        vitalsLbl = new JLabel("-");

        JPanel statRow = new JPanel(new GridLayout(1, 4, 15, 0));
        statRow.setOpaque(false);
        statRow.setMaximumSize(new Dimension(1400, 100));
        statRow.add(createCompactStatCard("Appts", apptsLbl));
        statRow.add(createCompactStatCard("Reports", reportsLbl));
        statRow.add(createCompactStatCard("Health", healthLbl));
        statRow.add(createCompactStatCard("Vitals", vitalsLbl));
        
        dashboardContent.add(Box.createVerticalStrut(20));
        dashboardContent.add(statRow);
        dashboardContent.add(Box.createVerticalStrut(30));

        // 2. Dashboard Sections (Quick Previews)
        String[] apptHeaders = {"Date", "Doctor", "Reason", "Status"};
        Object[][] apptData = loadAppointments(context);
        this.appointmentTableModel = new DefaultTableModel(apptData, apptHeaders);
        
        JPanel appointmentsSection = createDataSectionFromModel("Upcoming Appointments", this.appointmentTableModel);
        dashboardContent.add(appointmentsSection);
        
        dashboardContent.add(Box.createVerticalStrut(25));
        
        String[] recordHeaders = {"Document Name", "Date", "Category"};
        Object[][] recordData = loadMedicalRecords(context);
        this.recordsTableModel = new DefaultTableModel(recordData, recordHeaders);
        dashboardContent.add(createDataSectionFromModel("Recent Medical Records", this.recordsTableModel));

        dashboardContent.add(Box.createVerticalStrut(25));
        
        String[] billHeaders = {"Invoice #", "Service", "Amount"};
        Object[][] billData = loadBills(context);
        this.billingTableModel = new DefaultTableModel(billData, billHeaders);
        dashboardContent.add(createDataSectionFromModel("Recent Billing", this.billingTableModel));

        JScrollPane scrollPane = new JScrollPane(dashboardContent);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        mainArea.add(scrollPane, BorderLayout.CENTER);

        add(mainArea, BorderLayout.CENTER);
    }

    private Object[][] loadAppointments(ApplicationContext context) {
        try {
            AppointmentRepo appointmentRepo = context.getBean(AppointmentRepo.class);
            
            // Get appointments for the currently logged in Patient
            Long patientId = frame.getLoggedInUserId();
            // Default to 1L if null (for testing/bypassing login)
            if (patientId == null) {
                patientId = 1L;
            }
            List<Appointment> appointments = appointmentRepo.findByPatientId(patientId);
            
            if (appointments == null || appointments.isEmpty()) {
                return new Object[][]{{"No appointments found", "-", "-", "-"}};
            }
            
            Object[][] data = new Object[appointments.size()][4];
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appt = appointments.get(i);
                data[i][0] = "Appt #" + appt.getAppointmentId();
                data[i][1] = "Doc ID: " + appt.getDoctorId(); 
                data[i][2] = appt.getTypeOfAppointment();
                data[i][3] = appt.getStatus();
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[][]{{"Error loading appointments", "-", "-", "-"}};
        }
    }

    private Object[][] loadBills(ApplicationContext context) {
        try {
            BillRepo billRepo = context.getBean(BillRepo.class);
            Long patientId = frame.getLoggedInUserId();
            if (patientId == null) patientId = 1L;
            
            List<Bill> bills = billRepo.findByPatientId(patientId);
            if (bills == null || bills.isEmpty()) {
                return new Object[][]{{"No invoices found", "-", "-"}};
            }
            
            Object[][] data = new Object[bills.size()][3];
            for (int i = 0; i < bills.size(); i++) {
                Bill b = bills.get(i);
                data[i][0] = b.getInvoiceNumber();
                data[i][1] = b.getService();
                data[i][2] = "₹" + String.format("%.2f", b.getAmount() != null ? b.getAmount() : 0.0);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[][]{{"Error loading bills", "-", "-"}};
        }
    }
    
    private Object[][] loadMedicalRecords(ApplicationContext context) {
        try {
            MedicalRecordRepo recordRepo = context.getBean(MedicalRecordRepo.class);
            Long patientId = frame.getLoggedInUserId();
            if (patientId == null) patientId = 1L;
            
            List<MedicalRecord> records = recordRepo.findByPatientId(patientId);
            if (records == null || records.isEmpty()) {
                return new Object[][]{{"No records found", "-", "-"}};
            }
            
            Object[][] data = new Object[records.size()][3];
            for (int i = 0; i < records.size(); i++) {
                MedicalRecord r = records.get(i);
                data[i][0] = r.getDocumentName() != null ? r.getDocumentName() : "Untitled Document";
                data[i][1] = r.getDate() != null ? r.getDate() : "-";
                data[i][2] = r.getCategory() != null ? r.getCategory() : "-";
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[][]{{"Error loading records", "-", "-"}};
        }
    }
    
    public void refreshData(ApplicationContext context) {
        if (this.appointmentTableModel != null) {
            Object[][] newData = loadAppointments(context);
            this.appointmentTableModel.setDataVector(newData, new String[]{"Date", "Doctor", "Reason", "Status"});
            
            if (apptsLbl != null) {
                if (newData.length == 1 && "No appointments found".equals(newData[0][0])) {
                    apptsLbl.setText("0");
                } else {
                    apptsLbl.setText(String.valueOf(newData.length));
                }
                healthLbl.setText("92%");
                vitalsLbl.setText("120/80");
            }
        }
        if (this.recordsTableModel != null) {
            Object[][] recordData = loadMedicalRecords(context);
            this.recordsTableModel.setDataVector(recordData, new String[]{"Document Name", "Date", "Category"});
            if (reportsLbl != null) {
                if (recordData.length == 1 && "No records found".equals(recordData[0][0])) {
                    reportsLbl.setText("0");
                } else {
                    reportsLbl.setText(String.valueOf(recordData.length));
                }
            }
        }
        if (this.billingTableModel != null) {
            Object[][] newBillData = loadBills(context);
            this.billingTableModel.setDataVector(newBillData, new String[]{"Invoice #", "Service", "Amount"});
        }
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

    private JPanel createCompactStatCard(String title, JLabel v) {
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

        v.setFont(new Font("SansSerif", Font.BOLD, 22));
        v.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        v.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(v, BorderLayout.CENTER);

        return card;
    }

    private JPanel createDataSection(String title, String[] headers, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, headers);
        return createDataSectionFromModel(title, model);
    }
    
    private JPanel createDataSectionFromModel(String title, DefaultTableModel model) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        lbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
        section.add(lbl, BorderLayout.NORTH);

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
