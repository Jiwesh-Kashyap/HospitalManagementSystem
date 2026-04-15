package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DoctorListingPage extends JPanel {
    private final PromethiusFrame frame;
    private final ApplicationContext context;
    private final JPanel doctorListPanel;

    public DoctorListingPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        this.context = context;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        // --- Header (Deep Green) ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 60));
        
        JButton backHomeBtn = new JButton("◀ Home");
        backHomeBtn.setForeground(Color.WHITE);
        backHomeBtn.setContentAreaFilled(false);
        backHomeBtn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        backHomeBtn.addActionListener(e -> frame.showPage("LANDING"));
        
        JPanel backBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backBtns.setOpaque(false);
        backBtns.add(backHomeBtn);

        header.add(backBtns, BorderLayout.WEST);
        
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
        doctorListPanel.removeAll();
        try {
            DoctorRepo doctorRepo = context.getBean(DoctorRepo.class);
            Iterable<Doctor> allDoctors = doctorRepo.findAll();
            
            int count = 0;
            for (Doctor doc : allDoctors) {
                // Determine a pseudo-random price and exp for UI flair
                String price = "₹" + ((doc.getId() % 5) * 100 + 400); 
                String exp = (doc.getId() + 3) + " YEARS • MBBS";
                
                doctorListPanel.add(createDoctorListItem(doc.getId(), doc.getName(), doc.getSpecialisation(), exp, "Hospital Visit", price));
                doctorListPanel.add(Box.createVerticalStrut(20));
                count++;
            }
            
            if (count == 0) {
                doctorListPanel.add(new JLabel("No specialists available currently."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            doctorListPanel.add(new JLabel("Error verifying doctor network."));
        }
        doctorListPanel.revalidate();
        doctorListPanel.repaint();
    }

    private JPanel createDoctorListItem(Long docId, String name, String spec, String exp, String loc, String price) {
        JPanel card = new JPanel(new BorderLayout(25, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        card.setMaximumSize(new Dimension(850, 150));

        // --- WEST: Profile Icon ---
        JPanel iconPanel = new JPanel(new GridBagLayout());
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(80, 80));
        JLabel iconLbl = new JLabel("👤");
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 50));
        iconLbl.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        iconPanel.add(iconLbl);
        card.add(iconPanel, BorderLayout.WEST);

        // --- CENTER: Info ---
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
        
        info.add(Box.createVerticalGlue());
        info.add(n);
        info.add(s);
        info.add(e);
        info.add(l);
        info.add(Box.createVerticalGlue());
        card.add(info, BorderLayout.CENTER);

        // --- EAST: Price & Action ---
        JPanel action = new JPanel();
        action.setLayout(new BoxLayout(action, BoxLayout.Y_AXIS));
        action.setOpaque(false);
        action.setPreferredSize(new Dimension(200, 120));
        
        JLabel p = new JLabel(price);
        p.setFont(new Font("SansSerif", Font.BOLD, 24));
        p.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JButton book = new JButton("Book Now");
        book.setBackground(PromethiusFrame.APOLLO_BLUE);
        book.setForeground(Color.WHITE);
        book.setFont(new Font("SansSerif", Font.BOLD, 16));
        book.setAlignmentX(Component.RIGHT_ALIGNMENT);
        book.setPreferredSize(new Dimension(160, 40));
        book.addActionListener(ev -> {
            frame.setCurrentSelectedDoctorId(docId);
            frame.showPage("PATIENT_DOCTOR_PROFILE");
        });

        action.add(Box.createVerticalGlue());
        action.add(p);
        action.add(Box.createVerticalStrut(10));
        action.add(book);
        action.add(Box.createVerticalGlue());
        
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
