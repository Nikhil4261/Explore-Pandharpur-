package Explore.Pandharpur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Villages extends JFrame {
    private String username;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public Villages(String username) {
        this.username = username;
        initializeUI();
    }

    public Villages() {
        this.username = "Guest"; // Default username if not provided
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Villages of Pandharpur");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define colors
        Color bgColor = new Color(44, 62, 80);
        Color textColor = new Color(236, 240, 241);
        Color buttonColor = new Color(41, 128, 185);
        Color buttonTextColor = Color.WHITE;

        String[] columnNames = {"Village", "Population"};

        String[][] data = {
                {"Pandharpur","3,73,684"},
                {"Adhiv", "3,178"}, {"Ajansond", "2,497"}, {"Ajote", "719"}, {"Ambe", "4,313"},
                {"Ambechincholi", "1,847"}, {"Anawali", "4,327"}, {"Avhe", "2,339"}, {"Babhulgaon", "3,327"},
                {"Badalkot", "1,479"}, {"Bardi", "2,358"}, {"Bhalawani", "9,781"}, {"Bhandi Shegaon", "5,649"},
                {"Bhatumbare", "2,799"}, {"Bhose", "9,408"}, {"Bitargaon", "475"}, {"Bohali", "3,564"},
                {"Chale", "5,954"}, {"Chilaiwadi", "1,659"}, {"Chincholi Bhose", "1,297"}, {"Chinchumbe", "516"},
                {"Degaon", "4,566"}, {"Devade", "1,963"}, {"Dhondewadi", "3,012"}, {"Eklaspur", "1,695"},
                {"Fulchincholi", "5,648"}, {"Gadegaon", "6,465"}, {"Gardi", "3,226"}, {"Gopalpur", "6,918"},
                {"Gurasale", "5,289"}, {"Hole", "3,257"}, {"Ishwar Wathar", "1,483"}, {"Jadhavwadi", "1,603"},
                {"Jainwadi", "2,265"}, {"Jaloli", "3,024"}, {"Kanhapuri", "2,933"}, {"Karkamb", "17,456"},
                {"Karole", "3,385"}, {"Kasegaon", "16,197"}, {"Kauthali", "3,946"}, {"Keskarwadi", "2,019"},
                {"Kharatwadi", "1,332"}, {"Khardi", "7,068"}, {"Kharsoli", "2,412"}, {"Khed Bhalawani", "2,178"},
                {"Khed Bhose", "2,333"}, {"Kondharki", "969"}, {"Korty", "6,494"}, {"Lonarwadi", "1,015"},
                {"Magarwadi", "2,032"}, {"Mendhapur", "4,703"}, {"Mundhewadi", "3,074"}, {"Nali", "878"},
                {"Nandore", "2,592"}, {"Narayan Chincholi", "2,275"}, {"Nematwadi", "1,936"}, {"Nepatgaon", "1,223"},
                {"Ozewadi", "3,080"}, {"Palshi", "5,384"}, {"Pandharewadi", "3,025"}, {"Patvardhan Kuroli", "4,495"},
                {"Pehe", "1,770"}, {"Pirachi Kuroli", "4,782"}, {"Pohargaon", "1,840"}, {"Puluj", "5,772"},
                {"Pulujwadi", "2,018"}, {"Ranzani", "3,514"}, {"Ropale", "6,315"}, {"Sangavi", "1,472"},
                {"Sarkoli", "5,399"}, {"Shankargaon", "1,307"}, {"Shegaon Dumala", "3,098"}, {"Shelve", "3,342"},
                {"Shendgewadi", "1,285"}, {"Shetphal", "3,347"}, {"Shevate", "3,068"}, {"Shirgaon", "1,484"},
                {"Shirthon", "1,603"}, {"Siddhewadi", "2,334"}, {"Sonke", "3,463"}, {"Sugavabhose", "809"},
                {"Takaligursala", "606"}, {"Takli", "10,122"}, {"Tanali", "2,230"},{"Tarapur","3,493"}, {"Tungat", "5,275"},
                {"Ujani", "551"}, {"Umbare", "3,728"}, {"Umbargaon", "1,945"}, {"Upari", "3,151"},
                {"Venunagar", "2,211"}, {"Vite", "1,478"}, {"Wadi Kuroli", "2,271"}, {"Wakhari", "8,029"}
        };


        // Table Setup
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        table.setBackground(bgColor);
        table.setForeground(textColor);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(table);

        // Search Bar Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(bgColor);
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(textColor);
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchVillage();
            }
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.addActionListener(e -> refreshTable());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(refreshButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(231, 76, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard(username).setVisible(true);
        });

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(bgColor);
        footerPanel.add(backButton);

        // Layout Setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void searchVillage() {
        String searchText = searchField.getText().trim();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0));
    }

    private void refreshTable() {
        searchField.setText("");
        table.setRowSorter(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Villages().setVisible(true));
    }
}