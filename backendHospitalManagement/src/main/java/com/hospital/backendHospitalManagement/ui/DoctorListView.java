package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DoctorListView extends JPanel {
    private final PromethiusFrame frame;
    private final JPanel doctorGrid;
    private final JLabel titleLabel;

    public DoctorListView(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        // Header (Deep Green)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 70));
        
        JButton backBtn = new JButton("◀ Back to Home");
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        backBtn.addActionListener(e -> frame.showPage("LANDING"));
        header.add(backBtn, BorderLayout.WEST);
        
        titleLabel = new JLabel("Our Health Specialists", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel, BorderLayout.CENTER);
        
        add(header, BorderLayout.NORTH);

        // Filter / Search Bar (Turquoise)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        searchPanel.setBackground(new Color(245, 255, 255));
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        JTextField searchField = new JTextField(25);
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        JButton findBtn = new JButton("Filter Results");
        findBtn.setBackground(PromethiusFrame.CYAN);
        findBtn.setForeground(Color.WHITE);
        findBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        searchPanel.add(new JLabel("Find Specific Specialist: "));
        searchPanel.add(searchField);
        searchPanel.add(findBtn);
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.add(searchPanel, BorderLayout.NORTH);

        // Doctor Cards List (Vertical stack of horizontal rows)
        doctorGrid = new JPanel();
        doctorGrid.setLayout(new BoxLayout(doctorGrid, BoxLayout.Y_AXIS));
        doctorGrid.setBackground(PromethiusFrame.PURE_WHITE);
        doctorGrid.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JScrollPane scroll = new JScrollPane(doctorGrid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(28); // Fast, smooth scrolling
        mainContent.add(scroll, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);
        
        // Initial populate with all
        filterBySpecialty("General");
    }

    public void filterBySpecialty(String specialty) {
        titleLabel.setText(specialty + " Specialists - Promethius+");
        doctorGrid.removeAll();
        
        // Generate 3-4 doctors per specialty
        String[] firstNames = {"Alice", "Robert", "Samantha", "Vikram", "Elena", "Marcus"};
        String[] lastNames = {"Smith", "Johnson", "Kapoor", "Davis", "Chen", "Wilson"};
        String[] exps = {"12+ Years Exp", "8+ Years Exp", "15+ Years Exp", "6+ Years Exp"};

        int count = 3 + (int)(Math.random() * 2); // 3 or 4
        for (int i = 0; i < count; i++) {
            String name = "Dr. " + firstNames[(int)(Math.random() * 6)] + " " + lastNames[(int)(Math.random() * 6)];
            String exp = exps[(int)(Math.random() * 4)];
            doctorGrid.add(createDoctorCard(name, specialty, exp));
            doctorGrid.add(Box.createVerticalStrut(20)); // Spacing between rows
        }
        
        doctorGrid.revalidate();
        doctorGrid.repaint();
    }

    private JPanel createDoctorCard(String name, String spec, String exp) {
        JPanel card = new JPanel(new BorderLayout(25, 0));
        card.setBackground(PromethiusFrame.PURE_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        card.setMaximumSize(new Dimension(1000, 140));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- WEST: Profile Icon ---
        JPanel iconPanel = new JPanel(new GridBagLayout());
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(80, 80));
        JLabel iconLbl = new JLabel("👤");
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 50));
        iconLbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        iconPanel.add(iconLbl);
        card.add(iconPanel, BorderLayout.WEST);

        // --- CENTER: Doctor Info ---
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        
        JLabel n = new JLabel(name);
        n.setFont(new Font("SansSerif", Font.BOLD, 24));
        n.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        
        JLabel s = new JLabel(spec);
        s.setFont(new Font("SansSerif", Font.ITALIC, 18));
        s.setForeground(PromethiusFrame.CYAN);

        JLabel e = new JLabel(exp);
        e.setFont(new Font("SansSerif", Font.PLAIN, 16));
        e.setForeground(Color.GRAY);

        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(n);
        infoPanel.add(s);
        infoPanel.add(e);
        infoPanel.add(Box.createVerticalGlue());
        card.add(infoPanel, BorderLayout.CENTER);

        // --- EAST: Action Button ---
        JPanel actionPanel = new JPanel(new GridBagLayout());
        actionPanel.setOpaque(false);
        
        JButton book = new JButton("Book Appointment");
        book.setBackground(PromethiusFrame.APOLLO_BLUE);
        book.setForeground(Color.WHITE);
        book.setFont(new Font("SansSerif", Font.BOLD, 16));
        book.setPreferredSize(new Dimension(200, 45));
        book.setFocusPainted(false);
        book.addActionListener(ev -> {
            if (frame.isPatientLoggedIn()) {
                frame.showPage("PATIENT_BOOKING_LIST");
            } else {
                frame.showPage("LOGIN");
            }
        });
        
        actionPanel.add(book);
        card.add(actionPanel, BorderLayout.EAST);

        // Hover effect for card
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { card.setBackground(new Color(250, 252, 255)); }
            @Override
            public void mouseExited(MouseEvent e) { card.setBackground(PromethiusFrame.PURE_WHITE); }
        });

        return card;
    }
}
