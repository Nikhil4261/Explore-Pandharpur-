package Explore.Pandharpur;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EducationInstitutions extends JFrame {
    private JTable institutionsTable;
    private DefaultTableModel tableModel;
    private JButton backButton, searchButton, refreshButton;
    private JTextField searchField;
    private String username;
    private List<EducationalInstitution> institutions;

    public EducationInstitutions(String username) {
        this.username = username;
        setTitle("Education & Institutions in Pandharpur");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(44, 62, 80));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(52, 73, 94));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(52, 73, 94));

        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(41, 128, 185));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchInstitution());

        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(39, 174, 96)); // Green
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> refreshInstitutions());

        searchPanel.add(new JLabel("Search: ")).setForeground(Color.WHITE);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        topPanel.add(searchPanel, BorderLayout.EAST);

        JPanel institutionsPanel = new JPanel(new BorderLayout());
        institutionsPanel.setBorder(BorderFactory.createTitledBorder("List of Educational Institutions"));
        institutionsPanel.setBackground(new Color(44, 62, 80));
        String[] columns = {"Institution Name", "Type", "Address Details"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        institutionsTable = new JTable(tableModel);
        institutionsTable.setRowHeight(60);
        institutionsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        institutionsTable.setForeground(Color.BLACK);
        institutionsTable.setSelectionBackground(new Color(52, 152, 219));
        institutionsTable.setSelectionForeground(Color.WHITE);
        institutionsTable.getTableHeader().setBackground(new Color(41, 128, 185));
        institutionsTable.getTableHeader().setForeground(Color.WHITE);
        institutionsTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel) {
                    ((JLabel) c).setText("<html>" + value.toString() + "</html>");
                }
                return c;
            }
        });
        JScrollPane institutionsScrollPane = new JScrollPane(institutionsTable);
        institutionsPanel.add(institutionsScrollPane, BorderLayout.CENTER);

        institutions = getStaticInstitutionsData();
        loadInstitutionsData(institutions);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.setBackground(new Color(52, 73, 94));

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> navigateToDashboard());
        backPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(institutionsPanel, BorderLayout.CENTER);
        add(backPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void navigateToDashboard() {
        dispose();
        new Dashboard(username);
    }

    private void loadInstitutionsData(List<EducationalInstitution> data) {
        tableModel.setRowCount(0);
        for (EducationalInstitution institution : data) {
            tableModel.addRow(new Object[]{
                    institution.getName(),
                    institution.getType(),
                    institution.getContactDetails()
            });
        }
    }

    private void searchInstitution() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            loadInstitutionsData(institutions);
            return;
        }
        List<EducationalInstitution> filteredList = new ArrayList<>();
        for (EducationalInstitution institution : institutions) {
            if (institution.getName().toLowerCase().contains(query) ||
                    institution.getType().toLowerCase().contains(query)) {
                filteredList.add(institution);
            }
        }
        loadInstitutionsData(filteredList);
    }

    private void refreshInstitutions() {
        searchField.setText("");
        loadInstitutionsData(institutions);
    }

    private List<EducationalInstitution> getStaticInstitutionsData() {
        List<EducationalInstitution> institutions = new ArrayList<>();

        institutions.add(new EducationalInstitution("ICMS", "College", "Address: Old Kasegaon Road, Kasegaon, Pandharpur, Dist-Solapur"));
        institutions.add(new EducationalInstitution("Vivek Vardhini Vidyalaya", "Junior College", "Address: Old Kasegaon Road, Kasegaon, Pandharpur, Dist-Solapur"));
        institutions.add(new EducationalInstitution("Government ITI", "College", "Address: Old Kasegaon Road, Near Court, Pandharpur, Dist-Solapur"));
        institutions.add(new EducationalInstitution("SVERI College of Engineering", "Engineering College", "Address: Gopalpur-Ranjani Road, Gopalpur, Tal. Pandharpur, Dist. Solapur"));
        institutions.add(new EducationalInstitution("KBP College", "College", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("Uma Shikshanshastra Mahavidyalaya", "College", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("MIT Junior College", "Junior College", "Address: At Post Wakhari, Pune-Pandharpur Road, Pandharpur, Dist-Solapur"));
        institutions.add(new EducationalInstitution("SKN Sinhgad College of Engineering", "Engineering College", "Address: Korti, Pandharpur, Maharashtra 413304"));
        institutions.add(new EducationalInstitution("Shri Vithal Education & Research Institute (SVERI) Polytechnic", "Polytechnic College", "Address: Gopalpur, Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("Shri Vithal Education & Research Institute College of Pharmacy", "Pharmacy College", "Address: Gopalpur, Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("D.B.F. Dayanand College of Arts & Science", "Arts & Science College", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("Shri Pandharpur Urban Co-op Bank College of Commerce", "Commerce College", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("Shri Balaji Polytechnic College", "Polytechnic College", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("Dnyaneshwar Vidyalaya", "School", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("Vidyadham Prashala", "School", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("Modern High School", "School", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("Navjeevan English School & Junior College", "Junior College", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("S.K.N. Sinhgad College of Engineering", "Engineering College", "Address: At Post Korti, Taluka Pandharpur, Pandharpur-Karad Road, Dist. Solapur, Maharashtra"));
        institutions.add(new EducationalInstitution("Karmayogi Polytechnic College", "Polytechnic College", "Address: Pandharpur, Maharashtra"));
        institutions.add(new EducationalInstitution("New Satara College of Engineering and Management", "Engineering College", "Address: Korthi, Pandharpur, Maharashtra"));
        return institutions;
    }

    private static class EducationalInstitution {
        private final String name;
        private final String type;
        private final String contactDetails;

        public EducationalInstitution(String name, String type, String contactDetails) {
            this.name = name;
            this.type = type;
            this.contactDetails = contactDetails;
        }

        public String getName() { return name; }
        public String getType() { return type; }
        public String getContactDetails() { return contactDetails; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EducationInstitutions("TestUser"));
    }
}