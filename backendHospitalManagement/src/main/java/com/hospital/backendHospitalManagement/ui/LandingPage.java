package com.hospital.backendHospitalManagement.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LandingPage extends JPanel {
    private final PromethiusFrame frame;
    private JPanel authBtns;

    public LandingPage(PromethiusFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.PURE_WHITE);

        // --- Tier 1: Top Header (Deep Green) ---
        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        topHeader.setPreferredSize(new Dimension(1200, 80));
        topHeader.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // Logo
        JLabel logo = new JLabel("Promethius+");
        logo.setFont(new Font("SansSerif", Font.BOLD, 32));
        logo.setForeground(PromethiusFrame.CYAN);
        topHeader.add(logo, BorderLayout.WEST);

        // Global Search (Vibrant Turquoise accents)
        JPanel searchPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPane.setOpaque(false);
        JTextField searchField = new JTextField("Search for doctors, specialists, or conditions...", 35);
        searchField.setPreferredSize(new Dimension(400, 45));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        searchField.setBorder(BorderFactory.createLineBorder(PromethiusFrame.CYAN, 2));
        searchPane.add(searchField);
        topHeader.add(searchPane, BorderLayout.CENTER);

        // Login / Signup (Blue Accents)
        authBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        authBtns.setOpaque(false);
        refreshLoginState();
        topHeader.add(authBtns, BorderLayout.EAST);

        // --- Tier 2: Main Nav Bar (Black/White Contrast) ---
        JPanel mainNav = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        mainNav.setBackground(PromethiusFrame.PURE_WHITE);
        String[] navItems = {"Find Doctors", "Lab Tests", "Circle Membership", "Health Records", "Buy Insurance"};
        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(new Font("SansSerif", Font.BOLD, 18));
            label.setForeground(PromethiusFrame.PURE_BLACK);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { label.setForeground(PromethiusFrame.CYAN); }
                @Override
                public void mouseExited(MouseEvent e) { label.setForeground(PromethiusFrame.PURE_BLACK); }
                @Override
                public void mouseClicked(MouseEvent e) {
                   if (item.equals("Find Doctors")) frame.showPage("DOCTOR_SEARCH");
                }
            });
            mainNav.add(label);
        }

        // --- Tier 3: Specialty Bar (Deep Green) ---
        JPanel specNav = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 8));
        specNav.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        String[] specs = {"Cardiology", "Neurology", "Orthopedics", "Pediatrics", "Dermatology", "Oncology"};
        for (String s : specs) {
            JLabel label = new JLabel(s);
            label.setForeground(PromethiusFrame.PURE_WHITE);
            label.setFont(new Font("SansSerif", Font.PLAIN, 16));
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { frame.showDoctorSpecialty(s); }
                @Override
                public void mouseEntered(MouseEvent e) { label.setForeground(PromethiusFrame.CYAN); }
                @Override
                public void mouseExited(MouseEvent e) { label.setForeground(PromethiusFrame.PURE_WHITE); }
            });
            specNav.add(label);
        }

        JPanel headerStack = new JPanel();
        headerStack.setLayout(new BoxLayout(headerStack, BoxLayout.Y_AXIS));
        headerStack.add(topHeader);
        headerStack.add(mainNav);
        headerStack.add(specNav);
        add(headerStack, BorderLayout.NORTH);

        // --- Main Content (Vibrant & High Performance) ---
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(PromethiusFrame.PURE_WHITE);

        // Hero Section
        JPanel hero = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, PromethiusFrame.STAR_COMMAND_BLUE, getWidth(), getHeight(), new Color(0, 50, 0));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(PromethiusFrame.CYAN);
                g2.setFont(new Font("SansSerif", Font.BOLD, 48));
                
                String welcomeText = "Exceptional Care";
                String subText = "Connecting you to the world's best specialists.";
                
                if (frame.getCurrentUserName() != null) {
                    welcomeText = "Welcome Back, " + frame.getCurrentUserName() + "!";
                    subText = "Ready to continue your health journey?";
                }
                
                g2.drawString(welcomeText, 100, 120);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 24));
                g2.drawString(subText, 100, 170);
            }
        };
        hero.setPreferredSize(new Dimension(1200, 350));
        hero.setMaximumSize(new Dimension(2000, 350));
        content.add(hero);

        // Quick Actions
        JPanel quickActions = new JPanel(new GridLayout(1, 4, 30, 0));
        quickActions.setBackground(PromethiusFrame.PURE_WHITE);
        quickActions.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        quickActions.add(createActionCard("Doctor Appointment", "BOOK NOW", "👨‍⚕️", PromethiusFrame.APOLLO_BLUE, "PATIENT_BOOKING_LIST"));
        quickActions.add(createActionCard("Lab Tests", "BOOK AT HOME", "🔬", PromethiusFrame.CYAN, "LAB_TESTS"));
        quickActions.add(createActionCard("Health Insurance", "PROTECT NOW", "🛡️", PromethiusFrame.STAR_COMMAND_BLUE, "PATIENT_INSURANCE"));
        quickActions.add(createActionCard("Emergency", "CALL NOW", "🚑", Color.RED, "EMERGENCY_SUPPORT"));
        content.add(quickActions);

        // Healthcare Grid Section
        JLabel gridHeader = new JLabel("Browse by Healthcare Section");
        gridHeader.setFont(new Font("SansSerif", Font.BOLD, 32));
        gridHeader.setForeground(PromethiusFrame.STAR_COMMAND_BLUE);
        gridHeader.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 0));
        content.add(gridHeader);

        JPanel conditionGrid = new JPanel(new GridLayout(2, 5, 25, 25));
        conditionGrid.setBackground(PromethiusFrame.PURE_WHITE);
        conditionGrid.setBorder(BorderFactory.createEmptyBorder(0, 60, 60, 60));
        
        String[] titles = {"Cardiac Care", "Diabetes Care", "Neurology", "Orthopedics", "Pediatrics", "Stomach Care", "Dental Care", "Eye Care", "Skin Care", "Elderly Care"};
        String[] mapping = {"Cardiology", "Diabetes", "Neurology", "Orthopedics", "Pediatrics", "Gastroenterology", "Dental", "Ophthalmology", "Dermatology", "Geriatrics"};
        String[] icons = {"🫀", "🩸", "🧠", "🦴", "👶", "🤢", "🦷", "👁️", "✨", "👨‍🦳"};

        for (int i = 0; i < titles.length; i++) {
            conditionGrid.add(createConditionCard(titles[i], icons[i], mapping[i]));
        }
        content.add(conditionGrid);

        // Fix Scroll Performance
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(28); // Vibrant, fast scrolling
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createVibrantButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setFocusPainted(false);
        return btn;
    }

    public void refreshLoginState() {
        authBtns.removeAll();
        if (frame.isPatientLoggedIn() || frame.isDoctorLoggedIn()) {
            String btnText = frame.isPatientLoggedIn() ? "Patient Dashboard" : "Doctor Dashboard";
            String targetPage = frame.isPatientLoggedIn() ? "PATIENT_DASHBOARD" : "DOCTOR_DASHBOARD";
            
            JButton dashboardBtn = createVibrantButton(btnText, PromethiusFrame.APOLLO_BLUE);
            dashboardBtn.addActionListener(e -> frame.showPage(targetPage));
            
            JButton logoutBtn = createVibrantButton("Logout", Color.GRAY);
            logoutBtn.addActionListener(e -> frame.logout());
            
            authBtns.add(dashboardBtn);
            authBtns.add(logoutBtn);
        } else {
            JButton loginBtn = createVibrantButton("Login", PromethiusFrame.APOLLO_BLUE);
            loginBtn.addActionListener(e -> frame.showPage("LOGIN"));
            
            JButton signupBtn = createVibrantButton("Signup", PromethiusFrame.CYAN);
            signupBtn.addActionListener(e -> frame.showPage("SIGNUP"));
            
            authBtns.add(loginBtn);
            authBtns.add(signupBtn);
        }
        authBtns.revalidate();
        authBtns.repaint();
        this.revalidate();
        this.repaint();
    }

    private JPanel createActionCard(String title, String sub, String icon, Color color, String targetPage) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        JLabel iconLbl = new JLabel(icon, SwingConstants.CENTER);
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 50));
        card.add(iconLbl, BorderLayout.CENTER);

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel s = new JLabel(sub, SwingConstants.CENTER);
        s.setFont(new Font("SansSerif", Font.BOLD, 14));
        s.setForeground(color);
        info.add(t);
        info.add(s);
        card.add(info, BorderLayout.SOUTH);

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) { 
               if (frame.isPatientLoggedIn() || "LOGIN".equals(targetPage)) {
                   frame.showPage(targetPage); 
               } else {
                   frame.showPage("LOGIN");
               }
           }
        });
        return card;
    }

    private JPanel createConditionCard(String name, String icon, String specialty) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(PromethiusFrame.PURE_WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        
        JLabel iconLbl = new JLabel(icon, SwingConstants.CENTER);
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 46));
        card.add(iconLbl, BorderLayout.CENTER);

        JLabel text = new JLabel(name, SwingConstants.CENTER);
        text.setFont(new Font("SansSerif", Font.BOLD, 18));
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(text, BorderLayout.SOUTH);

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { frame.showDoctorSpecialty(specialty); }
            @Override
            public void mouseEntered(MouseEvent e) { card.setBackground(new Color(245, 255, 255)); }
            @Override
            public void mouseExited(MouseEvent e) { card.setBackground(PromethiusFrame.PURE_WHITE); }
        });

        return card;
    }
}
