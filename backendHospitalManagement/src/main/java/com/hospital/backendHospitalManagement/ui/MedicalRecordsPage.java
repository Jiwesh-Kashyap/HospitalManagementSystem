package com.hospital.backendHospitalManagement.ui;

import org.springframework.context.ApplicationContext;
import com.hospital.backendHospitalManagement.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class MedicalRecordsPage extends JPanel {
    private final PromethiusFrame frame;
    private final ApplicationContext context;
    private DefaultTableModel recordsTableModel;

    public MedicalRecordsPage(PromethiusFrame frame, ApplicationContext context) {
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
        backBtn.addActionListener(e -> {
            if (frame.isDoctorLoggedIn()) frame.showPage("DOCTOR_DASHBOARD");
            else frame.showPage("PATIENT_DASHBOARD");
        });
        header.add(backBtn, BorderLayout.WEST);
        
        JLabel title = new JLabel("Digital Health Records", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        String[] headers = {"Record ID", "Document Name", "Category", "Date", "Notes", "Attachment"};
        Object[][] data = loadMedicalRecords();
        recordsTableModel = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(recordsTableModel);
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        content.add(scrollPane, BorderLayout.CENTER);
        
        JButton openBtn = new JButton("⬇️ Download Attached PDF");
        openBtn.setBackground(PromethiusFrame.STAR_COMMAND_BLUE);
        openBtn.setForeground(Color.WHITE);
        openBtn.setFocusPainted(false);
        openBtn.addActionListener(ev -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a record from the table first.");
                return;
            }
            int modelRow = table.convertRowIndexToModel(row);
            String recStr = (String) recordsTableModel.getValueAt(modelRow, 0); 
            Long id = Long.parseLong(recStr.replace("REC-", ""));
            
            try {
                MedicalRecordRepo repo = context.getBean(MedicalRecordRepo.class);
                MedicalRecord record = repo.findById(id).orElse(null);
                
                if (record == null || record.getFileData() == null) {
                    JOptionPane.showMessageDialog(this, "No PDF attached to this record.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Medical Record");
                String defaultName = record.getAttachedFileName() != null ? record.getAttachedFileName() : "Medical_Record_" + id + ".pdf";
                fileChooser.setSelectedFile(new File(defaultName));
                
                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    Files.write(fileToSave.toPath(), record.getFileData());
                    JOptionPane.showMessageDialog(this, "File successfully downloaded to:\n" + fileToSave.getAbsolutePath(), "Download Complete", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error downloading PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(openBtn);
        content.add(bottomPanel, BorderLayout.SOUTH);
        
        add(content, BorderLayout.CENTER);
    }
    
    private Object[][] loadMedicalRecords() {
        try {
            MedicalRecordRepo recordRepo = context.getBean(MedicalRecordRepo.class);
            Long patientId = frame.getLoggedInUserId();
            if (patientId == null) patientId = 1L;
            
            List<MedicalRecord> records = recordRepo.findByPatientId(patientId);
            if (records == null || records.isEmpty()) {
                return new Object[][]{{"-", "No records found", "-", "-", "-", "-"}};
            }
            
            Object[][] data = new Object[records.size()][6];
            for (int i = 0; i < records.size(); i++) {
                MedicalRecord r = records.get(i);
                data[i][0] = "REC-" + r.getRecordId();
                data[i][1] = r.getDocumentName();
                data[i][2] = r.getCategory();
                data[i][3] = r.getDate();
                data[i][4] = r.getNotes();
                data[i][5] = (r.getFileData() != null) ? "📄 " + (r.getAttachedFileName() != null ? r.getAttachedFileName() : "PDF") : "None";
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[][]{{"-", "Error loading data", "-", "-", "-", "-"}};
        }
    }
    
    public void refreshData() {
        if (recordsTableModel != null) {
            recordsTableModel.setDataVector(loadMedicalRecords(), new String[]{"Record ID", "Document Name", "Category", "Date", "Notes", "Attachment"});
        }
    }
}

