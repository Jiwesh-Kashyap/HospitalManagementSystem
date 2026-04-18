package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BillingPage extends JPanel {
    private final PromethiusFrame frame;
    private final DefaultTableModel tableModel;

    public BillingPage(PromethiusFrame frame) {
        this.frame = frame;
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

        String[] cols = {"Invoice #", "Date", "Service", "Amount", "Status"};
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
            BillRepo repo = context.getBean(BillRepo.class);
            Long userId = frame.getLoggedInUserId();
            if (userId == null) return;

            List<Bill> list = repo.findByPatientId(userId);
            for (Bill b : list) {
                tableModel.addRow(new Object[]{
                    b.getInvoiceNumber(),
                    b.getDate(),
                    b.getService(),
                    "₹" + String.format("%.2f", b.getAmount() != null ? b.getAmount() : 0.0),
                    "Paid"
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
