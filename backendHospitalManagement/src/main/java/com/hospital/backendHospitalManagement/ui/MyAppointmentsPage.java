package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyAppointmentsPage extends JPanel {
    private final PromethiusFrame frame;
    private final DefaultTableModel tableModel;

    public MyAppointmentsPage(PromethiusFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.BEIGE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 60));
        
        JButton backBtn = new JButton("◀ Back to Dashboard");
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> {
            if (frame.isDoctorLoggedIn()) frame.showPage("DOCTOR_DASHBOARD");
            else frame.showPage("PATIENT_DASHBOARD");
        });
        header.add(backBtn, BorderLayout.WEST);
        
        JLabel title = new JLabel("My Scheduled Appointments", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        String[] cols = {"ID", "Doctor ID", "Type", "Status"};
        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        
        add(scroll, BorderLayout.CENTER);
    }

    public void refreshData(ApplicationContext context) {
        tableModel.setRowCount(0);
        try {
            AppointmentRepo repo = context.getBean(AppointmentRepo.class);
            Long userId = frame.getLoggedInUserId();
            if (userId == null) return;

            List<Appointment> list;
            if (frame.isDoctorLoggedIn()) {
                list = repo.findByDoctorId(userId);
            } else {
                list = repo.findByPatientId(userId);
            }

            for (Appointment appt : list) {
                tableModel.addRow(new Object[]{
                    appt.getAppointmentId(),
                    appt.getDoctorId(),
                    appt.getTypeOfAppointment(),
                    appt.getStatus()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
