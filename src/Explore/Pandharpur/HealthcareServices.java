package Explore.Pandharpur;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Hospital {
    String name, type, address;

    public Hospital(String name, String type, String address) {
        this.name = name;
        this.type = type;
        this.address = address;
    }
}

public class HealthcareServices extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private List<Hospital> hospitals;

    public HealthcareServices() {
        this("Guest");
    }

    public HealthcareServices(String username) {
        setTitle("Healthcare Services - Explore Pandharpur");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(30, 144, 255));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(240, 248, 255));

        searchField = new JTextField(20);
        searchField.setToolTipText("Search by Name, Type, or Address");
        JButton searchButton = new JButton("Search");
        JButton refreshButton = new JButton("Refresh");

        styleButton(searchButton);
        styleButton(refreshButton);

        searchButton.addActionListener(e -> searchHospital());
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            reloadHospitalData();
        });

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Hospital Name", "Type", "Address"}, 0);
        table = new JTable(model);
        styleTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.setBackground(new Color(220, 20, 60));
        backButton.addActionListener(e -> {
            this.dispose();
            try {
                new Dashboard(username).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dashboard not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadHospitalData();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleTable() {
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(173, 216, 230));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(70, 130, 180));
        header.setOpaque(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 144, 255));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void loadHospitalData() {
        hospitals = new ArrayList<>();
        hospitals.add(new Hospital("Lifeline Super Speciality Hospital", "Private", "Mahavir Nagar, Pandharpur"));
        hospitals.add(new Hospital("Samarth Hospital", "Private", "Bhakti Marg, Pandharpur"));
        hospitals.add(new Hospital("Anand Rishiji Hospital", "Private", "Near Market Yard, Pandharpur"));
        hospitals.add(new Hospital("Om Sai Hospital", "Private", "Mangalwar Peth, Pandharpur"));
        hospitals.add(new Hospital("Shree Hospital", "Private", "Shani Mandir Road, Pandharpur"));
        hospitals.add(new Hospital("Sub District Hospital", "Government", "Ram Bagh, Pandharpur"));
        hospitals.add(new Hospital("Borawake Hospital", "Government", "Bhakti Marg, Pandharpur"));
        hospitals.add(new Hospital("Rural Hospital", "Government", "Jijamata Nagar, Pandharpur"));
        hospitals.add(new Hospital("Taluka General Hospital", "Government", "Station Road, Pandharpur"));
        hospitals.add(new Hospital("Primary Health Centre", "Government", "Kasegaon, Pandharpur"));
        hospitals.add(new Hospital("Varadvinayak Multispeciality Hospital", "Multispeciality", "Karad Road, Pandharpur"));
        hospitals.add(new Hospital("Sahyadri Multispeciality Hospital", "Multispeciality", "Malhar Peth, Pandharpur"));
        hospitals.add(new Hospital("Vighnaharta Multispeciality Hospital", "Multispeciality", "Vithal Mandir Road, Pandharpur"));
        hospitals.add(new Hospital("City Care Multispeciality Hospital", "Multispeciality", "Navi Peth, Pandharpur"));
        hospitals.add(new Hospital("Sanjeevani Multispeciality Hospital", "Multispeciality", "Rajwada, Pandharpur"));
        reloadHospitalData();

    }

    private void reloadHospitalData() {
        model.setRowCount(0);
        for (Hospital h : hospitals) {
            model.addRow(new Object[]{h.name, h.type, h.address});
        }
    }

    private void searchHospital() {
        String searchText = searchField.getText().toLowerCase().trim();
        model.setRowCount(0);

        if (searchText.isEmpty()) {
            reloadHospitalData();
            return;
        }

        for (Hospital h : hospitals) {
            if (h.name.toLowerCase().contains(searchText) || h.type.toLowerCase().contains(searchText) || h.address.toLowerCase().contains(searchText)) {
                model.addRow(new Object[]{h.name, h.type, h.address});
            }
        }
    }

    public static void main(String[] args) {
        new HealthcareServices();
    }
}
