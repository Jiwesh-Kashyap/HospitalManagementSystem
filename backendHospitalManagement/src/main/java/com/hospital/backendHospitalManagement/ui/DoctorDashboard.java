package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

public class DoctorDashboard extends JPanel {
    private final PromethiusFrame frame;
    private final ApplicationContext context;
    private DefaultTableModel appointmentTableModel;
    private JTable appointmentTable;
    private List<Appointment> loadedAppointments;
    private JLabel titleLbl;
    private JLabel apptsStatLbl;
    private JLabel pendingStatLbl;
    private JLabel criticalStatLbl;

    public DoctorDashboard(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        this.context = context;
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
        logout.addActionListener(e -> frame.logout());
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
        titleLbl = new JLabel("Welcome, " + docName);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        titleBox.add(titleLbl);
        
        headerPanel.add(titleBox, BorderLayout.WEST);
        content.add(headerPanel, BorderLayout.NORTH);

        // Quick Stats
        apptsStatLbl = new JLabel("-");
        pendingStatLbl = new JLabel("-");
        criticalStatLbl = new JLabel("-");

        JPanel stats = new JPanel(new GridLayout(1, 3, 20, 0));
        stats.setOpaque(false);
        stats.setPreferredSize(new Dimension(800, 80));
        stats.add(createCompactStatCard("Today's Appts", apptsStatLbl));
        stats.add(createCompactStatCard("Pending Rounds", pendingStatLbl));
        stats.add(createCompactStatCard("Critical Alerts", criticalStatLbl));
        
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
        String[] headers = {"Appt ID", "Patient Name", "Reason", "Status"};
        Object[][] data = loadAppointmentsData();
        appointmentTableModel = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dashboardContent.add(Box.createVerticalStrut(40)); // Added extra vertical space manually requested by user

        JPanel appointmentsSection = createDataTableSection("Assigned Appointments (Double click to manage)", appointmentTableModel);
        dashboardContent.add(appointmentsSection);

        dashboardContent.add(Box.createVerticalStrut(30));

        JScrollPane scrollPane = new JScrollPane(dashboardContent);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        content.add(scrollPane, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createDataTableSection(String title, DefaultTableModel model) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);
        section.setMaximumSize(new Dimension(1400, 400));
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
        section.add(lbl, BorderLayout.NORTH);

        appointmentTable = new JTable(model);
        appointmentTable.setFont(PromethiusFrame.MAIN_FONT);
        appointmentTable.setRowHeight(40);
        appointmentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        appointmentTable.setShowGrid(true);
        appointmentTable.setGridColor(new Color(230, 230, 230));
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Add double click listener
        appointmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && appointmentTable.getSelectedRow() != -1) {
                    manageSelectedAppointment();
                }
            }
        });
        
        JScrollPane scroll = new JScrollPane(appointmentTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scroll.setPreferredSize(new Dimension(800, 300));
        section.add(scroll, BorderLayout.CENTER);

        return section;
    }

    private void manageSelectedAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow < 0 || loadedAppointments == null || selectedRow >= loadedAppointments.size()) return;

        Appointment appt = loadedAppointments.get(selectedRow);
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Manage Appointment", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridLayout(4, 1, 5, 5));
        form.add(new JLabel("Appointment ID: " + appt.getAppointmentId()));
        form.add(new JLabel("Type: " + appt.getTypeOfAppointment()));
        
        form.add(new JLabel("Status:"));
        JComboBox<String> statusDropdown = new JComboBox<>(new String[]{"Pending", "Confirmed", "Completed", "Cancelled"});
        statusDropdown.setSelectedItem(appt.getStatus() != null ? appt.getStatus() : "Pending");
        form.add(statusDropdown);

        p.add(form, BorderLayout.NORTH);

        JPanel notesPanel = new JPanel(new BorderLayout(5, 5));
        notesPanel.add(new JLabel("Doctor Notes:"), BorderLayout.NORTH);
        JTextArea notesArea = new JTextArea(appt.getNotes() != null ? appt.getNotes() : "");
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        notesPanel.add(new JScrollPane(notesArea), BorderLayout.CENTER);
        
        p.add(notesPanel, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Updates");
        saveBtn.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            try {
                String oldStatus = appt.getStatus();
                String newStatus = (String) statusDropdown.getSelectedItem();
                
                if ("Completed".equalsIgnoreCase(newStatus) && !"Completed".equalsIgnoreCase(oldStatus)) {
                    JPanel billPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                    billPanel.add(new JLabel("Service Delivered:"));
                    String type = appt.getTypeOfAppointment();
                    JTextField serviceField = new JTextField(type != null ? type : "Consultation");
                    billPanel.add(serviceField);
                    billPanel.add(new JLabel("Amount (\u20B9):"));
                    JTextField amountField = new JTextField("699.00");
                    billPanel.add(amountField);
                    
                    int result = JOptionPane.showConfirmDialog(dialog, billPanel, "Generate Bill", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            Double val = Double.parseDouble(amountField.getText());
                            Bill b = new Bill(appt.getPatientId(), appt.getDoctorId(), appt.getAppointmentId(), 
                                                "INV-" + java.time.LocalDate.now().getYear() + "-" + appt.getAppointmentId(), 
                                                serviceField.getText(), val, java.time.LocalDate.now().toString());
                            context.getBean(BillRepo.class).save(b);
                        } catch(Exception parseEx) {
                            JOptionPane.showMessageDialog(dialog, "Invalid amount entered. Bill generation skipped.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }

                appt.setStatus(newStatus);
                appt.setNotes(notesArea.getText());
                context.getBean(AppointmentRepo.class).save(appt);
                JOptionPane.showMessageDialog(dialog, "Appointment updated!");
                dialog.dispose();
                refreshData(context);
            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error saving: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        p.add(saveBtn, BorderLayout.SOUTH);
        dialog.add(p);
        dialog.setVisible(true);
    }

    public void refreshData(ApplicationContext context) {
        if (appointmentTableModel != null) {
            Object[][] data = loadAppointmentsData();
            appointmentTableModel.setDataVector(data, new String[]{"Appt ID", "Patient Name", "Reason", "Status"});
        }
        if (titleLbl != null) {
            String docName = frame.getCurrentUserName() != null ? frame.getCurrentUserName() : "Physician";
            String prefix = docName.startsWith("Dr.") ? "" : "Dr. ";
            titleLbl.setText("Welcome, " + prefix + docName);
        }
        if (apptsStatLbl != null && loadedAppointments != null) {
            int pendingCount = 0;
            int criticalCount = 0;
            for (Appointment a : loadedAppointments) {
                String s = (a.getStatus() != null) ? a.getStatus() : "";
                if (s.equalsIgnoreCase("Pending") || s.equalsIgnoreCase("Waiting")) pendingCount++;
                if (s.equalsIgnoreCase("Critical")) criticalCount++;
            }
            apptsStatLbl.setText(String.valueOf(loadedAppointments.size()));
            pendingStatLbl.setText(String.valueOf(pendingCount));
            criticalStatLbl.setText(String.valueOf(criticalCount));
        }
    }

    private Object[][] loadAppointmentsData() {
        try {
            PersonRepo pRepo = context.getBean(PersonRepo.class);
            AppointmentRepo appRepo = context.getBean(AppointmentRepo.class);

            String email = frame.getCurrentUserEmail();
            Long doctorId = 1L; // fallback
            if (email != null) {
                Optional<Person> docOpt = pRepo.findByEmail(email);
                if (docOpt.isPresent()) {
                    doctorId = docOpt.get().getId();
                }
            }

            loadedAppointments = appRepo.findByDoctorId(doctorId);
            if (loadedAppointments == null || loadedAppointments.isEmpty()) {
                return new Object[][]{{"-", "No appointments assigned", "-", "-"}};
            }

            Object[][] data = new Object[loadedAppointments.size()][4];
            for (int i = 0; i < loadedAppointments.size(); i++) {
                Appointment app = loadedAppointments.get(i);
                data[i][0] = String.valueOf(app.getAppointmentId());
                
                String pName = "Patient #" + app.getPatientId();
                if (app.getPatientId() != null) {
                    Optional<Person> pOpt = pRepo.findById(app.getPatientId());
                    if (pOpt.isPresent()) pName = pOpt.get().getName();
                }
                data[i][1] = pName;
                data[i][2] = app.getTypeOfAppointment();
                data[i][3] = app.getStatus() != null ? app.getStatus() : "Pending";
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[][]{{"Error loading data", "-", "-", "-"}};
        }
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
}
