package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import javax.swing.*;
import java.awt.*;

public class DoctorBookingPage extends JPanel {
    private final PromethiusFrame frame;
    private final JPanel slotGrid;
    private final JLabel selectedTimeLbl;

    public DoctorBookingPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
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

        JLabel name = new JLabel("Dr. Chaithra H");
        name.setFont(new Font("SansSerif", Font.BOLD, 28));
        name.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        
        JLabel spec = new JLabel("General Physician / Internal Medicine Specialist");
        spec.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        JLabel exp = new JLabel("6+ years experience");
        exp.setFont(new Font("SansSerif", Font.BOLD, 16));
        exp.setForeground(PromethiusFrame.APOLLO_BLUE);

        profile.add(name);
        profile.add(spec);
        profile.add(exp);
        profile.add(Box.createVerticalStrut(20));
        profile.add(new JLabel("Qualifications: MBBS, MD (General Medicine)"));
        profile.add(new JLabel("Languages: English, Kannada, Hindi"));
        profile.add(Box.createVerticalStrut(20));
        profile.add(new JLabel("📍 Apollo 24|7 Clinic - Bangalore"));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.6; gbc.weighty = 1.0;
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

        // Date Slider Mock
        JPanel dateSlider = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        dateSlider.setOpaque(false);
        String[] days = {"Mon 13", "Tue 14", "Wed 15", "Thu 16", "Fri 17", "Sat 18"};
        for (String day : days) {
            JButton dayBtn = new JButton(day);
            dayBtn.setPreferredSize(new Dimension(80, 50));
            dayBtn.setBackground(day.equals("Mon 13") ? PromethiusFrame.CYAN : Color.WHITE);
            dayBtn.setForeground(day.equals("Mon 13") ? PromethiusFrame.STAR_COMMAND_BLUE : Color.BLACK);
            dateSlider.add(dayBtn);
        }
        slotContainer.add(Box.createVerticalStrut(20));
        slotContainer.add(dateSlider);

        // Time Grid
        slotGrid = new JPanel(new GridLayout(0, 4, 10, 10));
        slotGrid.setOpaque(false);
        String[] times = {"10:10 PM", "10:20 PM", "10:30 PM", "10:40 PM", "10:50 PM"};
        for (String t : times) {
            JButton tBtn = new JButton(t);
            tBtn.setFocusPainted(false);
            tBtn.setBackground(Color.WHITE);
            tBtn.setForeground(Color.BLACK); // Explicitly set text color for visibility
            tBtn.addActionListener(e -> selectTime(t));
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

    private void selectTime(String t) {
        selectedTimeLbl.setText("Selected Appointment Time: " + t);
        selectedTimeLbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
    }

    private void finalizeBooking() {
        if (selectedTimeLbl.getText().contains("Selected")) {
            JOptionPane.showMessageDialog(this, "Appointment Scheduled Successfully for " + selectedTimeLbl.getText().split(": ")[1] + "!");
            frame.showPage("PATIENT_DASHBOARD");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a time slot first.");
        }
    }
}
