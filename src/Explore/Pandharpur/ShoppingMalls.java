package Explore.Pandharpur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ShoppingMalls extends JFrame {
    private DefaultTableModel tableModel;
    private JTable mallTable;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private String username;

    public ShoppingMalls(String username) {
        this.username = username;
        setTitle("Shopping Malls in Pandharpur");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Title Label
        JLabel titleLabel = new JLabel("\uD83C\uDFE2 Shopping Malls in Pandharpur", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 150));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Table Data
        String[] columnNames = {"Mall Name", "Location", "Contact"};
        String[][] malls = {
                {"Pandharpur City Mall", "Main Road, Pandharpur", "+91-9876543210"},
                {"Solapur Mart", "Near Bus Stand, Pandharpur", "+91-9988776655"},
                {"Lifestyle Plaza", "Market Yard, Pandharpur", "+91-7788991122"},
                {"Fashion Hub", "Station Road, Pandharpur", "+91-6677889900"},
                {"Mega Mart", "Mangalwar Peth, Pandharpur", "+91-8123456789"},
                {"Pandharpur Central", "Ganesh Nagar, Pandharpur", "+91-8987654321"},
                {"D-Mart", "Near Bharti Vidyapeeth, Pandharpur", "+91-7008123456"},
                {"Reliance Trends", "Karmaveer Nagar, Pandharpur", "+91-7890654321"},
                {"Vishal Mega Mart", "Opposite ST Stand, Pandharpur", "+91-9156789023"},
                {"Big Bazaar", "Alandi Road, Pandharpur", "+91-9876123450"},
                {"City Centre Mall", "Sangola Road, Pandharpur", "+91-7568901234"},
                {"Shree Plaza", "Pandharpur Market Yard", "+91-9876543012"},
                {"Smart Bazaar", "Solapur Road, Pandharpur", "+91-9087654321"},
                {"Metro Mart", "Near Railway Station, Pandharpur", "+91-8087654321"},
                {"Grand Mall", "Lakshmi Road, Pandharpur", "+91-6067891234"}
        };

        // JTable Model
        tableModel = new DefaultTableModel(malls, columnNames);
        mallTable = new JTable(tableModel);
        mallTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mallTable.setRowHeight(30);
        mallTable.setSelectionBackground(new Color(100, 150, 200));
        mallTable.setSelectionForeground(Color.WHITE);
        mallTable.setGridColor(new Color(200, 200, 200));

        JTableHeader header = mallTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);

        rowSorter = new TableRowSorter<>(tableModel);
        mallTable.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(mallTable);
        add(scrollPane, BorderLayout.CENTER);

        // Search Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchField = new JTextField(20);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setToolTipText("Search malls by name or location...");

        JButton searchBtn = createButton("Search", new Color(50, 150, 250));
        searchBtn.addActionListener(e -> searchTable());

        JButton refreshBtn = createButton("Refresh", new Color(34, 177, 76));
        refreshBtn.addActionListener(e -> refreshTable());

        topPanel.add(new JLabel("🔍 Search: "));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        // Back Button Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = createButton("Back", Color.RED);
        backBtn.addActionListener(e -> {
            new Dashboard(username);
            dispose();
        });
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    private void searchTable() {
        String text = searchField.getText().trim();
        if (text.length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private void refreshTable() {
        searchField.setText("");
        rowSorter.setRowFilter(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShoppingMalls("User").setVisible(true));
    }
}
