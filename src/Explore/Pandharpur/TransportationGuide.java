package Explore.Pandharpur;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransportationGuide extends JFrame {
    private String username;
    private JTabbedPane tabbedPane;
    private JButton backBtn, searchBtn, refreshBtn;
    private JTextField searchField;
    private JTable busTable, trainTable, rickshawTable;
    private DefaultTableModel busModel, trainModel, rickshawModel;

    public TransportationGuide(String username) {
        this.username = username;
        setTitle("Explore Pandharpur - Transportation Guide");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Verdana", Font.BOLD, 16));

        busModel = new DefaultTableModel(getBusData(), new String[]{"Route", "Timings", "Fare"});
        trainModel = new DefaultTableModel(getTrainData(), new String[]{"Route", "Timings", "Fare"});
        rickshawModel = new DefaultTableModel(getRickshawData(), new String[]{"Route", "Timings", "Fare"});

        busTable = createTable(busModel);
        trainTable = createTable(trainModel);
        rickshawTable = createTable(rickshawModel);

        tabbedPane.addTab("Bus", new JScrollPane(busTable));
        tabbedPane.addTab("Train", new JScrollPane(trainTable));
        tabbedPane.addTab("Rickshaw", new JScrollPane(rickshawTable));

        add(tabbedPane, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchBtn = new JButton("Search");
        refreshBtn = new JButton("Refresh");

        searchBtn.addActionListener(e -> searchTransport());
        refreshBtn.addActionListener(e -> refreshTables());

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);
        add(searchPanel, BorderLayout.NORTH);

        // Back Button
        backBtn = new JButton("Back");
        backBtn.setBackground(Color.RED);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Verdana", Font.BOLD, 16));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            new Dashboard(username);
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Verdana", Font.BOLD, 18));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setFont(new Font("Arial", Font.PLAIN, 16));
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, cellRenderer);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);

        return table;
    }

    private void searchTransport() {
        String query = searchField.getText().trim().toLowerCase();
        filterTable(busModel, getBusData(), query);
        filterTable(trainModel, getTrainData(), query);
        filterTable(rickshawModel, getRickshawData(), query);
    }

    private void filterTable(DefaultTableModel model, String[][] data, String query) {
        model.setRowCount(0);
        for (String[] row : data) {
            for (String field : row) {
                if (field.toLowerCase().contains(query)) {
                    model.addRow(row);
                    break;
                }
            }
        }
    }

    private void refreshTables() {
        busModel.setDataVector(getBusData(), new String[]{"Route", "Timings", "Fare"});
        trainModel.setDataVector(getTrainData(), new String[]{"Route", "Timings", "Fare"});
        rickshawModel.setDataVector(getRickshawData(), new String[]{"Route", "Timings", "Fare"});
    }

    // Hardcoded Bus Data
    private String[][] getBusData() {
        return new String[][]{
                {"Pandharpur - Solapur", "06:00 AM, 12:00 PM, 06:00 PM", "₹100"},
                {"Pandharpur - Pune", "07:00 AM, 02:00 PM, 09:00 PM", "₹300"},
                {"Pandharpur - Mumbai", "08:00 AM, 04:00 PM, 11:00 PM", "₹500"},
                {"Pandharpur - Nashik", "06:30 AM, 01:30 PM, 08:30 PM", "₹400"},
                {"Pandharpur - Kolhapur", "07:15 AM, 03:15 PM, 11:15 PM", "₹350"},
                {"Pandharpur - Aurangabad", "05:45 AM, 01:45 PM, 09:45 PM", "₹450"},
                {"Pandharpur - Ahmednagar", "06:00 AM, 02:00 PM, 10:00 PM", "₹320"},
                {"Pandharpur - Satara", "07:30 AM, 03:30 PM, 11:30 PM", "₹280"},
                {"Pandharpur - Sangli", "08:00 AM, 04:00 PM, 12:00 AM", "₹260"},
                {"Pandharpur - Latur", "05:30 AM, 01:30 PM, 09:30 PM", "₹350"},
                {"Pandharpur - Beed", "06:15 AM, 02:15 PM, 10:15 PM", "₹370"},
                {"Pandharpur - Osmanabad", "07:00 AM, 03:00 PM, 11:00 PM", "₹300"},
                {"Pandharpur - Jalgaon", "08:30 AM, 04:30 PM, 12:30 AM", "₹600"},
                {"Pandharpur - Dhule", "05:00 AM, 01:00 PM, 09:00 PM", "₹650"},
                {"Pandharpur - Amravati", "06:45 AM, 02:45 PM, 10:45 PM", "₹750"},
                {"Pandharpur - Akola", "07:30 AM, 03:30 PM, 11:30 PM", "₹700"},
                {"Pandharpur - Chandrapur", "08:15 AM, 04:15 PM, 12:15 AM", "₹800"},
                {"Pandharpur - Wardha", "05:45 AM, 01:45 PM, 09:45 PM", "₹720"},
                {"Pandharpur - Bhandara", "06:30 AM, 02:30 PM, 10:30 PM", "₹780"},
                {"Pandharpur - Nagpur", "07:30 AM, 03:30 PM, 11:30 PM", "₹850"},
                {"Pandharpur - Baramati", "08:45 AM, 04:45 PM, 12:45 AM", "₹300"},
                {"Pandharpur - Miraj", "06:15 AM, 02:15 PM, 10:15 PM", "₹270"},
                {"Pandharpur - Karad", "07:45 AM, 03:45 PM, 11:45 PM", "₹320"}
        };
    }

    // Hardcoded Train Data
    private String[][] getTrainData() {
        return new String[][]{
                {"Pandharpur - Solapur", "05:30 AM, 01:30 PM, 08:30 PM", "₹80"},
                {"Pandharpur - Pune", "06:15 AM, 03:00 PM, 10:45 PM", "₹250"},
                {"Pandharpur - Mumbai", "07:00 AM, 05:15 PM, 11:50 PM", "₹400"},
                {"Pandharpur - Nashik", "06:30 AM, 02:30 PM, 10:30 PM", "₹350"},
                {"Pandharpur - Kolhapur", "07:15 AM, 03:15 PM, 11:15 PM", "₹300"},
                {"Pandharpur - Aurangabad", "08:00 AM, 04:00 PM, 12:00 AM", "₹450"},
                {"Pandharpur - Ahmednagar", "05:30 AM, 01:30 PM, 09:30 PM", "₹320"},
                {"Pandharpur - Satara", "06:15 AM, 02:15 PM, 10:15 PM", "₹280"},
                {"Pandharpur - Sangli", "07:00 AM, 03:00 PM, 11:00 PM", "₹260"},
                {"Pandharpur - Latur", "08:30 AM, 04:30 PM, 12:30 AM", "₹400"},
                {"Pandharpur - Nagpur", "05:45 AM, 01:45 PM, 09:45 PM", "₹900"},
                {"Pandharpur - Akola", "06:30 AM, 02:30 PM, 10:30 PM", "₹750"},
                {"Pandharpur - Chandrapur", "07:15 AM, 03:15 PM, 11:15 PM", "₹850"},
                {"Pandharpur - Wardha", "08:00 AM, 04:00 PM, 12:00 AM", "₹780"},
                {"Pandharpur - Jalna", "09:30 AM, 05:30 PM, 01:30 AM", "₹410"},
                {"Pandharpur - Parbhani", "06:45 AM, 02:45 PM, 10:45 PM", "₹460"}
        };
    }

    // Hardcoded Rickshaw Data
    private String[][] getRickshawData() {
        return new String[][]{
                {"Bus Stand - Vitthal Temple", "Available Anytime", "₹50"},
                {"Railway Station - Temple", "Available Anytime", "₹40"},
                {"City Tour", "Custom Timings", "₹200"},
                {"Market Area - Home", "Available Anytime", "₹30"},
                {"Hospital - Home", "On Demand", "₹60"},
                {"Railway Station - Bus Stand", "Available Anytime", "₹25"},
                {"City Shopping Ride", "Custom Timings", "₹150"},
                {"School Pick-up", "Custom Timings", "₹100"},
                {"Marriage Function Rental", "Custom Timings", "₹500"},
                {"Local Travel", "On Demand", "₹80"},
                {"Pandharpur - MIDC Area", "Available Anytime", "₹120"},
                {"Temple to Sangola Road", "Available Anytime", "₹70"},
                {"Market to Railway Station", "Available Anytime", "₹50"},
                {"City to Industrial Area", "On Demand", "₹180"},
                {"Hostel to College", "Custom Timings", "₹90"},
                {"Airport Pickup (Solapur)", "On Demand", "₹1200"},
                {"Local Festive Ride", "Seasonal Availability", "₹250"},
                {"Temple Visit Special", "Available Anytime", "₹150"},
                {"Night Travel (City Limits)", "On Demand", "₹200"}
        };
    }
    public static void main(String[] args) {
        new TransportationGuide("Test User");
    }
}