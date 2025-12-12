package Explore.Pandharpur;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CityNews extends JFrame {
    private JTable newsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private final List<NewsArticle> newsData;
    private final String username;

    public CityNews(String username) {
        this.username = username;
        this.newsData = getStaticNewsData(); // Cached news data
        setupFrame();
        setupNewsPanel();
        setupBackButton();
        loadNewsData(newsData);
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Pandharpur City News & Updates");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(39, 55, 70));
    }

    private void setupNewsPanel() {
        JPanel newsPanel = new JPanel(new BorderLayout());
        newsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2), "Latest News Articles",
                2, 2, new Font("Arial", Font.BOLD, 16), Color.WHITE));
        newsPanel.setBackground(new Color(39, 55, 70));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(39, 55, 70));

        JLabel searchLabel = new JLabel("Search News: ");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchLabel.setForeground(Color.WHITE);

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterNewsBySearch(searchField.getText().toLowerCase());
            }
        });

        // Refresh Button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        refreshButton.setPreferredSize(new Dimension(100, 30));
        refreshButton.addActionListener(e -> refreshNewsData());

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(refreshButton); // Adding Refresh Button
        newsPanel.add(topPanel, BorderLayout.NORTH);

        setupNewsTable();
        newsPanel.add(new JScrollPane(newsTable), BorderLayout.CENTER);
        add(newsPanel, BorderLayout.CENTER);
    }

    private void setupNewsTable() {
        String[] columns = {"Title", "Details", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        newsTable = new JTable(tableModel);
        newsTable.setRowHeight(50);
        newsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        newsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        newsTable.getTableHeader().setBackground(new Color(52, 152, 219));
        newsTable.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(236, 240, 241) : Color.WHITE);
                if (isSelected) {
                    c.setBackground(new Color(173, 216, 230));
                }
                return c;
            }
        };

        for (int i = 0; i < newsTable.getColumnCount(); i++) {
            newsTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void setupBackButton() {
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.setBackground(new Color(39, 55, 70));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(192, 57, 43));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard(username);
        });

        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);
    }

    private void loadNewsData(List<NewsArticle> articles) {
        tableModel.setRowCount(0);
        for (NewsArticle article : articles) {
            tableModel.addRow(new Object[]{article.getTitle(), article.getDetails(), article.getDate()});
        }
    }

    private void filterNewsBySearch(String query) {
        List<NewsArticle> filtered = new ArrayList<>();
        for (NewsArticle article : newsData) {
            if (article.getTitle().toLowerCase().contains(query) || article.getDetails().toLowerCase().contains(query)) {
                filtered.add(article);
            }
        }
        loadNewsData(filtered);
    }

    // Refresh news data to restore original articles
    private void refreshNewsData() {
        searchField.setText(""); // Clear search field
        loadNewsData(getStaticNewsData()); // Reload original news
    }

    private List<NewsArticle> getStaticNewsData() {
        List<NewsArticle> articles = new ArrayList<>();
        articles.add(new NewsArticle("₹73 Crore Development Plan Approved", "Vitthal-Rukmini Temple to undergo major restoration.", "2025-02-15"));
        articles.add(new NewsArticle("₹129 Crore Sanctioned for Temple Facilities", "Modern darshan mandap and queue system to be built.", "2025-02-20"));
        articles.add(new NewsArticle("₹2,700 Crore Redevelopment Plan Announced", "Infrastructure upgrade to enhance pilgrimage experience.", "2025-02-10"));
        articles.add(new NewsArticle("Pandharpur Wari 2025 Begins", "Millions of devotees embark on the sacred journey.", "2025-06-30"));
        articles.add(new NewsArticle("Smart City Project Expansion", "New roads, streetlights, and public transport improvements.", "2025-03-05"));
        articles.add(new NewsArticle("Eco-Friendly Initiatives Launched", "Authorities promote green practices and waste management.", "2025-01-25"));
        articles.add(new NewsArticle("Record Tourist Footfall", "Pandharpur sees an all-time high in pilgrim visits.", "2025-02-28"));
        articles.add(new NewsArticle("Digital Queue System Introduced", "Tech-driven darshan booking system for convenience.", "2025-01-18"));
        articles.add(new NewsArticle("Special Train Services for Pilgrims", "Railway department launches direct services to Pandharpur.", "2025-04-01"));
        articles.add(new NewsArticle("Solar Energy Initiative", "Temple complex to be powered by renewable energy.", "2025-03-12"));
        return articles;
    }

    private static class NewsArticle {
        private final String title, details, date;
        public NewsArticle(String title, String details, String date) {
            this.title = title;
            this.details = details;
            this.date = date;
        }
        public String getTitle() { return title; }
        public String getDetails() { return details; }
        public String getDate() { return date; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CityNews("Test User"));
    }
}
