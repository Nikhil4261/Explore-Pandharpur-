package Explore.Pandharpur;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FoodRestaurants extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private String[][] originalData;

    public FoodRestaurants(String username) {
        setTitle("Explore Pandharpur - Food & Restaurants");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Label
        JLabel title = new JLabel("🍽️ Food & Restaurants in Pandharpur", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204)); // Dark Blue Background
        title.setPreferredSize(new Dimension(getWidth(), 50));
        add(title, BorderLayout.NORTH);

        originalData = new String[][]{
              //  String[][] data = {
                {"Shree Rajbhog Veg Thali", "Gujarati Vegetarian", "5/5", "Central Pandharpur", "11 AM - 11 PM"},
                {"Hotel Radhesh Garden", "Pure Vegetarian", "4.5/5", "Near city center", "10 AM - 10 PM"},
                {"Govinda's Restaurant", "Multi-Cuisine", "4.5/5", "ISKCON Pandharpur", "9 AM - 9 PM"},
                {"Hotel Vithal Kamat", "Pure Vegetarian", "4/5", "Central Pandharpur", "8 AM - 10 PM"},
                {"Mohi Cafe Pandharpur", "Fast Food", "3.5/5", "Maharaja Bakery", "10 AM - 10:30 PM"},
                {"Balaji Idli Point", "South Indian", "4/5", "Central Pandharpur", "Until 12 AM"},
                {"Royal Biryani House", "Biryani, Indian", "4/5", "City center", "Until 11:30 PM"},
                {"Sagar Ratna", "South Indian, Fast Food", "4.5/5", "Near Bus Stand", "7 AM - 11 PM"},
                {"Vithal Kamat", "Indian, Snacks", "4/5", "Near Pandharpur Temple", "7 AM - 10 PM"},
                {"Anand Restaurant", "Pure Vegetarian", "4.5/5", "Near Railway Station", "8 AM - 9 PM"},
                {"Shree Gajanan Maharaj Restaurant", "Vegetarian", "5/5", "Shirdi Road", "7 AM - 9 PM"},
                {"Suryakiran Hotel", "Pure Vegetarian, Indian", "4/5", "Near Pandharpur Bus Station", "9 AM - 10 PM"},
                {"Tandoor House", "Tandoori, Mughlai", "4/5", "Central Pandharpur", "10 AM - 11 PM"},
                {"Pind Punjabi Restaurant", "Punjabi, North Indian", "4.5/5", "Old City Area", "9 AM - 10:30 PM"},
                {"Sai Bhakti Restaurant", "Vegetarian, Fast Food", "4/5", "Opposite to Bus Stand", "10 AM - 11 PM"}
        };

        String[] columnNames = {"Name", "Cuisine Type", "Rating & Reviews", "Location", "Opening Hours"};
        tableModel = new DefaultTableModel(originalData, columnNames);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);

        // Table Header Styling
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBackground(new Color(0, 153, 255));

        // Center Align Text in Table Cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Search Bar
        JPanel topPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        JButton refreshBtn = new JButton("Refresh");

        searchBtn.addActionListener(e -> searchTable());
        refreshBtn.addActionListener(e -> refreshTable());

        topPanel.add(new JLabel("Search: "));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setBackground(Color.RED);
        backBtn.setForeground(Color.WHITE);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            new Dashboard(username);
            dispose();
        });

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void searchTable() {
        String searchText = searchField.getText().toLowerCase();
        DefaultTableModel filteredModel = new DefaultTableModel(new String[]{"Name", "Cuisine Type", "Rating & Reviews", "Location", "Opening Hours"}, 0);

        for (String[] row : originalData) {
            for (String cell : row) {
                if (cell.toLowerCase().contains(searchText)) {
                    filteredModel.addRow(row);
                    break;
                }
            }
        }

        table.setModel(filteredModel);
    }

    private void refreshTable() {
        table.setModel(new DefaultTableModel(originalData, new String[]{"Name", "Cuisine Type", "Rating & Reviews", "Location", "Opening Hours"}));
        searchField.setText("");
    }

    public static void main(String[] args) {
        new FoodRestaurants("Test User");
    }
}
