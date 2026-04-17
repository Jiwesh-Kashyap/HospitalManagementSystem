package com.hospital.backendHospitalManagement.ui;

import com.formdev.flatlaf.FlatLightLaf;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;


public class PromethiusFrame extends JFrame {

    private final ApplicationContext context;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    // Store the logged-in user's ID so that pages like PatientDashboard know who is active
    private Long loggedInUserId;

    public static final Color STAR_COMMAND_BLUE = new Color(0, 123, 184);
    public static final Color CYAN = new Color(0, 191, 255); // Deep Sky Blue / Cyan hybrid
    public static final Color APOLLO_BLUE = new Color(0, 0, 255);
    public static final Color BEIGE = new Color(245, 245, 245);
    public static final Color PURE_BLACK = Color.BLACK;
    public static final Color PURE_WHITE = Color.WHITE;
    public static final Font MAIN_FONT = new Font("SansSerif", Font.PLAIN, 18);
    public static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 24);

    private String currentUserEmail = null;
    private String currentUserRole = null;
    private String currentUserName = null;

    private Long currentSelectedDoctorId;

    public PromethiusFrame(ApplicationContext context) {
        this.context = context;
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);

        setupTheme();
        initUI();
    }

    private void setupTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("Button.background", CYAN);
            UIManager.put("Button.foreground", PURE_WHITE);
            UIManager.put("Label.font", MAIN_FONT);
            UIManager.put("Button.font", MAIN_FONT);
            UIManager.put("TextField.font", MAIN_FONT);
            UIManager.put("Panel.background", PURE_WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        setTitle("Promethius+ Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Define Pages (Placeholder panels for now)
        mainPanel.add(new LandingPage(this), "LANDING");
        mainPanel.add(new LoginPage(this, context), "LOGIN");
        mainPanel.add(new SignupPage(this, context), "SIGNUP");
        mainPanel.add(new PatientDashboard(this, context), "PATIENT_DASHBOARD");
        mainPanel.add(new DoctorDashboard(this, context), "DOCTOR_DASHBOARD");
        mainPanel.add(new DoctorListView(this, context), "DOCTOR_SEARCH");
        mainPanel.add(new DoctorBookingPage(this, context), "PATIENT_DOCTOR_PROFILE");
        mainPanel.add(new DoctorListingPage(this, context), "PATIENT_BOOKING_LIST");
        
        // New Feature Pages
        mainPanel.add(new InsurancePage(this), "PATIENT_INSURANCE");
        mainPanel.add(new EmergencyPage(this), "EMERGENCY_SUPPORT");
        mainPanel.add(new LabTestsPage(this), "LAB_TESTS");

        // Doctor Specific Pages
        mainPanel.add(new DoctorRoundsPage(this), "DOCTOR_ROUNDS");
        mainPanel.add(new DoctorPatientRecordsPage(this), "DOCTOR_PATIENT_RECORDS");
        mainPanel.add(new DoctorLabResultsPage(this), "DOCTOR_LAB_RESULTS");
        mainPanel.add(new MyAppointmentsPage(this), "PATIENT_APPOINTMENTS");
        mainPanel.add(new MedicalRecordsPage(this, context), "PATIENT_RECORDS");
        mainPanel.add(new BillingPage(this), "PATIENT_BILLING");
        mainPanel.add(new ProfilePage(this), "PATIENT_PROFILE");
        mainPanel.add(new SupportPage(this), "PATIENT_SUPPORT");

        add(mainPanel);
        showPage("LANDING");
    }

    public void showPage(String pageName) {
        for (Component comp : mainPanel.getComponents()) {
            if ("PATIENT_DASHBOARD".equals(pageName) && comp instanceof PatientDashboard) {
                ((PatientDashboard) comp).refreshData(context);
            } else if ("DOCTOR_DASHBOARD".equals(pageName) && comp instanceof DoctorDashboard) {
                ((DoctorDashboard) comp).refreshData(context);
            } else if ("PATIENT_DOCTOR_PROFILE".equals(pageName) && comp instanceof DoctorBookingPage) {
                ((DoctorBookingPage) comp).refreshData(context);
            }
        }
        cardLayout.show(mainPanel, pageName);
    }

    public void setLoggedInUserId(Long id) {
        this.loggedInUserId = id;
    }

    public Long getLoggedInUserId() {
        return this.loggedInUserId;
    }

    public void setLoggedIn(String email, String role, String name) {
        this.currentUserEmail = email;
        this.currentUserRole = role;
        this.currentUserName = name;
        refreshPages();
    }

    public void logout() {
        this.currentUserEmail = null;
        this.currentUserRole = null;
        this.currentUserName = null;
        this.loggedInUserId = null;
        refreshPages();
        showPage("LANDING");
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public boolean isPatientLoggedIn() {
        return "PATIENT".equalsIgnoreCase(currentUserRole);
    }

    public boolean isDoctorLoggedIn() {
        return "DOCTOR".equalsIgnoreCase(currentUserRole);
    }

    private void refreshPages() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof LandingPage) {
                ((LandingPage) comp).refreshLoginState();
            }
        }
    }

    public Long getCurrentSelectedDoctorId() { return currentSelectedDoctorId; }
    public void setCurrentSelectedDoctorId(Long doctorId) { this.currentSelectedDoctorId = doctorId; }

    public void showDoctorSpecialty(String specialty) {
        // Find the DoctorListView component and filter it
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof DoctorListView) {
                ((DoctorListView) comp).filterBySpecialty(specialty);
                break;
            }
        }
        showPage("DOCTOR_SEARCH");
    }

    public void launch() {
        EventQueue.invokeLater(() -> setVisible(true));
    }
}
