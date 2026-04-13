package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import javax.swing.*;
import java.awt.*;

public class DoctorListingPage extends JPanel {
    private final PromethiusFrame frame;
    private final JPanel doctorListPanel;

    public DoctorListingPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        // --- Header (Deep Green) ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 60));
        
        JButton backBtn = new JButton("◀ Back to Dashboard");
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        backBtn.addActionListener(e -> frame.showPage("PATIENT_DASHBOARD"));
        header.add(backBtn, BorderLayout.WEST);
        
        JLabel title = new JLabel("Find Your Specialist - Promethius+", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // --- Left Sidebar (Filters) ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(280, 800));
        sidebar.setBackground(new Color(250, 250, 250));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        sidebar.setBorder(BorderFactory.createCompoundBorder(sidebar.getBorder(), BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        sidebar.add(createFilterHeader("Filters"));
        sidebar.add(Box.createVerticalStrut(20));
        
        sidebar.add(createFilterLabel("Mode of Consult"));
        sidebar.add(new JCheckBox("Hospital Visit", true));
        sidebar.add(new JCheckBox("Online Consult", true));
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(createFilterLabel("Experience (In Years)"));
        sidebar.add(new JCheckBox("0-5 Years"));
        sidebar.add(new JCheckBox("6-10 Years", true));
        sidebar.add(new JCheckBox("11-16 Years"));
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(createFilterLabel("Fees (In Rupees)"));
        sidebar.add(new JCheckBox("100 - 500"));
        sidebar.add(new JCheckBox("500 - 1000", true));
        sidebar.add(new JCheckBox("1000+"));
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(createFilterLabel("Language"));
        sidebar.add(new JCheckBox("English", true));
        sidebar.add(new JCheckBox("Hindi", true));
        sidebar.add(new JCheckBox("Others"));

        add(sidebar, BorderLayout.WEST);

        // --- Main Doctor List ---
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(PromethiusFrame.PURE_WHITE);

        JPanel listHeader = new JPanel(new BorderLayout());
        listHeader.setOpaque(false);
        listHeader.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        
        JLabel countLbl = new JLabel("Consult Internal Medicine Specialists (12 doctors found)");
        countLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        listHeader.add(countLbl, BorderLayout.WEST);

        doctorListPanel = new JPanel();
        doctorListPanel.setLayout(new BoxLayout(doctorListPanel, BoxLayout.Y_AXIS));
        doctorListPanel.setBackground(PromethiusFrame.PURE_WHITE);
        
        // Add Mock Doctor Cards
        populateDoctors();

        JScrollPane scroll = new JScrollPane(doctorListPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(28);
        
        mainContent.add(listHeader, BorderLayout.NORTH);
        mainContent.add(scroll, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }

    private void populateDoctors() {
        doctorListPanel.add(createDoctorListItem("Dr. Chaithra H", "Internal Medicine Specialist", "6 YEARS • MBBS, MD", "Bangalore", "₹699"));
        doctorListPanel.add(Box.createVerticalStrut(20));
        doctorListPanel.add(createDoctorListItem("Dr. Summaiya Banu", "General Practitioner", "8 YEARS • MBBS", "Hyderabad", "₹660"));
        doctorListPanel.add(Box.createVerticalStrut(20));
        doctorListPanel.add(createDoctorListItem("Dr. Syed Ismail Ali", "General Physician", "12 YEARS • MBBS, MD", "Mumbai", "₹900"));
    }

    private JPanel createDoctorListItem(String name, String spec, String exp, String loc, String price) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setMaximumSize(new Dimension(850, 180));

        // Info Section
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        
        JLabel n = new JLabel(name);
        n.setFont(new Font("SansSerif", Font.BOLD, 22));
        n.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        
        JLabel s = new JLabel(spec);
        s.setFont(new Font("SansSerif", Font.PLAIN, 16));
        s.setForeground(Color.GRAY);
        
        JLabel e = new JLabel(exp);
        e.setFont(new Font("SansSerif", Font.BOLD, 14));
        e.setForeground(PromethiusFrame.APOLLO_BLUE);
        
        JLabel l = new JLabel("📍 " + loc);
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        info.add(n);
        info.add(s);
        info.add(Box.createVerticalStrut(5));
        info.add(e);
        info.add(Box.createVerticalStrut(5));
        info.add(l);
        
        card.add(info, BorderLayout.CENTER);

        // Price & Action
        JPanel action = new JPanel();
        action.setLayout(new BoxLayout(action, BoxLayout.Y_AXIS));
        action.setOpaque(false);
        action.setPreferredSize(new Dimension(200, 150));
        
        JLabel p = new JLabel(price, SwingConstants.RIGHT);
        p.setFont(new Font("SansSerif", Font.BOLD, 24));
        p.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JButton book = new JButton("Online Consult");
        book.setBackground(PromethiusFrame.CYAN);
        book.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        book.setFont(new Font("SansSerif", Font.BOLD, 16));
        book.setAlignmentX(Component.RIGHT_ALIGNMENT);
        book.addActionListener(ev -> frame.showPage("PATIENT_DOCTOR_PROFILE"));

        action.add(Box.createVerticalGlue());
        action.add(p);
        action.add(Box.createVerticalStrut(15));
        action.add(book);
        
        card.add(action, BorderLayout.EAST);

        return card;
    }

    private JLabel createFilterHeader(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 20));
        return l;
    }

    private JLabel createFilterLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 16));
        l.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        return l;
    }
}
