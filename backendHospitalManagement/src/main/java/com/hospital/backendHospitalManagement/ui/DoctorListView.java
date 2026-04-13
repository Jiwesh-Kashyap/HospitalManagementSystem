package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import javax.swing.*;
import java.awt.*;

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

        // Doctor Cards Grid
        doctorGrid = new JPanel(new GridLayout(0, 3, 30, 30));
        doctorGrid.setBackground(PromethiusFrame.PURE_WHITE);
        doctorGrid.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

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
        }
        
        doctorGrid.revalidate();
        doctorGrid.repaint();
    }

    private JPanel createDoctorCard(String name, String spec, String exp) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PromethiusFrame.PURE_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel n = new JLabel(name);
        n.setFont(new Font("SansSerif", Font.BOLD, 22));
        n.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        n.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel s = new JLabel(spec);
        s.setFont(new Font("SansSerif", Font.ITALIC, 18));
        s.setForeground(PromethiusFrame.CYAN);
        s.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel e = new JLabel(exp);
        e.setFont(new Font("SansSerif", Font.PLAIN, 16));
        e.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton book = new JButton("Book Appointment");
        book.setBackground(PromethiusFrame.APOLLO_BLUE);
        book.setForeground(Color.WHITE);
        book.setFont(new Font("SansSerif", Font.BOLD, 16));
        book.setAlignmentX(Component.CENTER_ALIGNMENT);
        book.setFocusPainted(false);
        book.addActionListener(ev -> frame.showPage("LOGIN"));

        card.add(n);
        card.add(Box.createVerticalStrut(5));
        card.add(s);
        card.add(Box.createVerticalStrut(5));
        card.add(e);
        card.add(Box.createVerticalStrut(20));
        card.add(book);

        // Hover effect for card
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return card;
    }
}
