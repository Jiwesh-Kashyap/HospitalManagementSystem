package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BillingPage extends JPanel {
    private final PromethiusFrame frame;
    private final ApplicationContext context;
    private DefaultTableModel billingTableModel;

    public BillingPage(PromethiusFrame frame, ApplicationContext context) {
        this.frame = frame;
        this.context = context;
        setLayout(new BorderLayout());
        setBackground(PromethiusFrame.BEIGE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        header.setPreferredSize(new Dimension(1200, 60));
        
        JButton backBtn = new JButton("◀ Back to Dashboard");
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> frame.showPage("PATIENT_DASHBOARD"));
        header.add(backBtn, BorderLayout.WEST);
        
        JLabel title = new JLabel("Invoice & Billing History", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        String[] headers = {"Invoice #", "Service Delivered", "Date", "Amount", "Status"};
        Object[][] data = loadBills();
        billingTableModel = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };



        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        content.add(scrollPane, BorderLayout.CENTER);
        
        add(content, BorderLayout.CENTER);
    }
    
    private Object[][] loadBills() {
        try {
            BillRepo billRepo = context.getBean(BillRepo.class);
            Long patientId = frame.getLoggedInUserId();
            if (patientId == null) patientId = 1L;
            
            List<Bill> bills = billRepo.findByPatientId(patientId);
            if (bills == null || bills.isEmpty()) {
                return new Object[][]{{"No invoices found", "-", "-", "-", "-"}};
            }
            
            Object[][] data = new Object[bills.size()][5];
            for (int i = 0; i < bills.size(); i++) {
                Bill b = bills.get(i);
                data[i][0] = b.getInvoiceNumber();
                data[i][1] = b.getService() != null ? b.getService() : "Consultation";
                data[i][2] = b.getDate() != null ? b.getDate() : "N/A";
                data[i][3] = "₹" + String.format("%.2f", b.getAmount() != null ? b.getAmount() : 0.0);
                data[i][4] = "Paid"; // Defaulting to Paid as per user's logic
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[][]{{"Error loading bills", "-", "-", "-", "-"}};
        }
    }
    
    public void refreshData() {
        if (billingTableModel != null) {
            billingTableModel.setDataVector(loadBills(), new String[]{"Invoice #", "Service Delivered", "Date", "Amount", "Status"});
        }
    }
}

