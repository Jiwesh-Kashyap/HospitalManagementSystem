package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;

import javax.swing.*;
import java.awt.*;

public class DoctorBookingPage extends JPanel {
    private final PromethiusFrame frame;
    private final ApplicationContext context;
    private final JPanel slotGrid;
    private final JLabel selectedTimeLbl;
    private final java.util.List<JButton> timeSlotButtons = new java.util.ArrayList<>();
    private final java.util.List<JButton> dayButtons = new java.util.ArrayList<>();
    private final JLabel nameLbl;
    private final JLabel specLbl;

    public DoctorBookingPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        this.context = context;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        // --- Header ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.PURE_WHITE);
        header.setPreferredSize(new Dimension(1200, 50));
        header.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));
        
        JLabel breadcrumbs = new JLabel("Home > Doctors > Internal Medicine > Bangalore");
        breadcrumbs.setForeground(PromethiusFrame.CYAN);
        header.add(breadcrumbs, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // --- Main Layout ---
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(PromethiusFrame.PURE_WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 30, 20, 30);

        // Left Panel: Doctor Profile
        JPanel profile = new JPanel();
        profile.setLayout(new BoxLayout(profile, BoxLayout.Y_AXIS));
        profile.setBackground(PromethiusFrame.PURE_WHITE);
        profile.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        nameLbl = new JLabel("Dr. Name Placeholder");
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 28));
        nameLbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        
        specLbl = new JLabel("Specialization Placeholder");
        specLbl.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        JLabel exp = new JLabel("6+ years experience");
        exp.setFont(new Font("SansSerif", Font.BOLD, 16));
        exp.setForeground(PromethiusFrame.APOLLO_BLUE);

        JPanel headerBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerBox.setOpaque(false);

        JLabel iconLbl = new JLabel("👤 ");
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 100)); // Slightly larger to cover more height
        iconLbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        headerBox.add(iconLbl);

        JPanel infoBox = new JPanel();
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setOpaque(false);
        infoBox.add(nameLbl);
        infoBox.add(specLbl);
        infoBox.add(exp);
        infoBox.add(Box.createVerticalStrut(20));
        infoBox.add(new JLabel("Qualifications: MBBS, MD (General Medicine)"));
        infoBox.add(new JLabel("Languages: English, Kannada, Hindi"));
        infoBox.add(Box.createVerticalStrut(20));
        infoBox.add(new JLabel("📍 Apollo 24|7 Clinic - Bangalore"));
        headerBox.add(infoBox);

        profile.add(headerBox);
        profile.add(Box.createVerticalStrut(30));


        JLabel bioHeader = new JLabel("Professional Biography");
        bioHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        bioHeader.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        profile.add(bioHeader);
        profile.add(Box.createVerticalStrut(10));

        JTextArea bio = new JTextArea(
            "Dr. Chaithra H is a highly acclaimed specialist with over 6 years of intensive experience in Internal Medicine. " +
            "Representing a dedication to patient-centric care, she has successfully managed complex clinical cases across " +
            "multispecialty institutions. Her expertise encompasses chronic disease management, preventive healthcare, and " +
            "comprehensive diagnostic evaluations. Known for her compassionate approach, she ensures every patient receives " +
            "tailored treatment plans that prioritize long-term wellness and recovery."
        );
        bio.setLineWrap(true);
        bio.setWrapStyleWord(true);
        bio.setEditable(false);
        bio.setBackground(PromethiusFrame.PURE_WHITE);
        bio.setFont(new Font("SansSerif", Font.ITALIC, 14));
        bio.setForeground(Color.DARK_GRAY);
        profile.add(bio);
        profile.add(Box.createVerticalGlue());

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.55; gbc.weighty = 1.0;
        main.add(profile, gbc);

        // Right Panel: Booking
        JPanel booking = new JPanel(new BorderLayout());
        booking.setBackground(PromethiusFrame.PURE_WHITE);
        booking.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel bookTitle = new JLabel("In-Clinic Consult - ₹699");
        bookTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        booking.add(bookTitle, BorderLayout.NORTH);

        JPanel slotContainer = new JPanel();
        slotContainer.setLayout(new BoxLayout(slotContainer, BoxLayout.Y_AXIS));
        slotContainer.setBackground(PromethiusFrame.PURE_WHITE);

        // Date Selector Redesign: Vertical Row Layout
        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
        dayPanel.setOpaque(false);
        
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM d");

        for (int i = 0; i < 7; i++) {
            java.time.LocalDate date = today.plusDays(i);
            String day = date.format(formatter);
            JButton dBtn = new JButton(day);
            dBtn.setFocusPainted(false);
            dBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            dBtn.setMaximumSize(new Dimension(350, 45));
            dBtn.setBackground(i == 0 ? PromethiusFrame.CYAN : Color.WHITE);
            dBtn.setForeground(i == 0 ? PromethiusFrame.STAR_COMMAND_BLUE : Color.BLACK);
            dBtn.addActionListener(e -> selectDay(dBtn));
            dayButtons.add(dBtn);
            dayPanel.add(dBtn);
            dayPanel.add(Box.createVerticalStrut(8));
        }
        slotContainer.add(Box.createVerticalStrut(20));
        slotContainer.add(dayPanel);

        // Time Grid
        slotGrid = new JPanel(new GridLayout(0, 4, 10, 10));
        slotGrid.setOpaque(false);
        String[] times = {"10:10 PM", "10:20 PM", "10:30 PM", "10:40 PM", "10:50 PM"};
        for (String t : times) {
            JButton tBtn = new JButton(t);
            tBtn.setFocusPainted(false);
            tBtn.setBackground(Color.WHITE);
            tBtn.setForeground(Color.BLACK);
            tBtn.addActionListener(e -> {
                selectTime(t);
                highlightTimeButton(tBtn);
            });
            timeSlotButtons.add(tBtn);
            slotGrid.add(tBtn);
        }
        slotContainer.add(Box.createVerticalStrut(30));
        slotContainer.add(new JLabel("Select Time Slot:"));
        slotContainer.add(Box.createVerticalStrut(10));
        slotContainer.add(slotGrid);

        selectedTimeLbl = new JLabel("Please select a time slot");
        selectedTimeLbl.setFont(new Font("SansSerif", Font.ITALIC, 14));
        slotContainer.add(Box.createVerticalStrut(20));
        slotContainer.add(selectedTimeLbl);

        JButton scheduleBtn = new JButton("Schedule Appointment");
        scheduleBtn.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        scheduleBtn.setForeground(Color.WHITE);
        scheduleBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        scheduleBtn.setPreferredSize(new Dimension(300, 50));
        scheduleBtn.addActionListener(e -> finalizeBooking());
        
        booking.add(slotContainer, BorderLayout.CENTER);
        booking.add(scheduleBtn, BorderLayout.SOUTH);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.4;
        main.add(booking, gbc);

        add(main, BorderLayout.CENTER);
    }

    private void selectDay(JButton selectedBtn) {
        for (JButton btn : dayButtons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }
        selectedBtn.setBackground(PromethiusFrame.CYAN);
        selectedBtn.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
    }

    private void highlightTimeButton(JButton selectedBtn) {
        for (JButton btn : timeSlotButtons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }
        selectedBtn.setBackground(PromethiusFrame.CYAN);
        selectedBtn.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
    }

    private void selectTime(String t) {
        selectedTimeLbl.setText("Selected Appointment Time: " + t);
        selectedTimeLbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
    }

    private void finalizeBooking() {
        if (selectedTimeLbl.getText().contains("Selected")) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to schedule this appointment?", 
                "Confirm Appointment", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                AppointmentRepo appRepo = context.getBean(AppointmentRepo.class);
                
                Appointment appt = new Appointment();
                
                Long pId = frame.getLoggedInUserId();
                if (pId == null) pId = 1L; // fallback
                
                Long doctorIdToBook = frame.getCurrentSelectedDoctorId();
                if (doctorIdToBook == null) doctorIdToBook = 1L;

                appt.setPatientId(pId);
                appt.setDoctorId(doctorIdToBook); 
                appt.setTypeOfAppointment("In-Clinic - " + selectedTimeLbl.getText().split(": ")[1]);
                appt.setStatus("Waiting");
                
                appRepo.save(appt);
                
                JOptionPane.showMessageDialog(this, "Appointment Scheduled Successfully!");
                frame.showPage("PATIENT_DASHBOARD");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error storing appointment in DB.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a time slot first.");
        }
    }

    public void refreshData(ApplicationContext context) {
        try {
            Long docId = frame.getCurrentSelectedDoctorId();
            if (docId != null) {
                DoctorRepo docRepo = context.getBean(DoctorRepo.class);
                java.util.Optional<Doctor> dOpt = docRepo.findById(docId);
                if (dOpt.isPresent()) {
                    Doctor doc = dOpt.get();
                    nameLbl.setText(doc.getName().startsWith("Dr.") ? doc.getName() : "Dr. " + doc.getName());
                    specLbl.setText(doc.getSpecialisation() != null ? doc.getSpecialisation() : "General Physician");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

    
