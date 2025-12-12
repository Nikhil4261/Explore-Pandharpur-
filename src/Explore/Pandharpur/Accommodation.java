package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Accommodation extends JFrame {
    private String username;
    private JPanel accommodationPanel;
    private JScrollPane scrollPane;
    private JButton backBtn, refreshBtn;
    private JTextField searchField;
    private JButton searchBtn;

    private static final String[][] ACCOMMODATIONS = {
            {"Hotel Vitthal Inn", "₹2,500/night", "Rating: 4.5/5", "Near Vitthal Mandir, Pandharpur", "Free Wi-Fi, AC, Restaurant", "Contact: 9876543210"},
            {"Pandharpur Lodge", "₹1,000/night", "Rating: 3.2/5", "Main Market Road, Pandharpur", "Basic Rooms, Parking Available", "Contact: 9765432109"},
            {"Sai Residency", "₹1,800/night", "Rating: 4.0/5", "Station Road, Pandharpur", "Free Breakfast, AC, Parking", "Contact: 9854321098"},
            {"Shree Guesthouse", "₹800/night", "Rating: 3.5/5", "Near Bus Stand, Pandharpur", "Budget Rooms, Room Service", "Contact: 9843210987"},
            {"Luxury Stay Inn", "₹3,500/night", "Rating: 5.0/5", "MG Road, Pandharpur", "Luxury Suites, Spa, Gym", "Contact: 9832109876"},
            {"Hotel Dhanashree", "₹2,000/night", "Rating: 4.2/5", "Solapur Highway, Pandharpur", "Family Rooms, Free Wi-Fi", "Contact: 9821098765"},
            {"Vitthal Rukmini Residency", "₹1,500/night", "Rating: 4.0/5", "Temple Road, Pandharpur", "Spacious Rooms, Temple View", "Contact: 9810987654"},
            {"Govardhan Tourist Lodge", "₹900/night", "Rating: 3.8/5", "Near Railway Station", "Restaurant, AC Rooms", "Contact: 9809876543"},
            {"Anand Bhavan", "₹1,700/night", "Rating: 4.1/5", "Bhagwat Nagar, Pandharpur", "Vegetarian Food, Quiet Location", "Contact: 9798765432"},
            {"Sopanam Lodge", "₹750/night", "Rating: 3.4/5", "Behind Bus Stand, Pandharpur", "Affordable Stay, Fan Rooms", "Contact: 9787654321"},
            {"MTDC Bhakta Niwas", "₹600/night", "Rating: 3.7/5", "Govt. Tourist Complex, Pandharpur", "Dormitory, Budget Stay", "Contact: 9776543210"},
            {"Tulsi Residency", "₹2,200/night", "Rating: 4.3/5", "Shani Chowk, Pandharpur", "Business Hotel, Banquet Hall", "Contact: 9765432109"},
            {"Hotel Bhakti Nivas", "₹1,300/night", "Rating: 4.0/5", "Near Vitthal Temple", "Pilgrim Stay, Free Wi-Fi", "Contact: 9754321098"},
            {"Radha Krishna Guesthouse", "₹1,000/night", "Rating: 3.6/5", "Gandhi Nagar, Pandharpur", "Simple Rooms, Laundry Service", "Contact: 9743210987"},
            {"Shree Govind Hotel", "₹1,800/night", "Rating: 4.0/5", "City Center, Pandharpur", "Restaurant, Family Suites", "Contact: 9732109876"},
            {"Gokul Lodge", "₹950/night", "Rating: 3.5/5", "Market Area, Pandharpur", "Budget Stay, No AC", "Contact: 9721098765"},
            {"Surabhi Residency", "₹2,400/night", "Rating: 4.5/5", "Near Highway Exit", "Modern Rooms, Conference Hall", "Contact: 9710987654"},
            {"Ashirwad Bhavan", "₹700/night", "Rating: 3.3/5", "Opp. Bus Stand, Pandharpur", "Basic Stay, Non-AC", "Contact: 9709876543"},
            {"Sai Palace", "₹2,800/night", "Rating: 4.6/5", "Near Temple Premises", "Deluxe Rooms, Breakfast", "Contact: 9698765432"},
            {"Sangam Grand", "₹3,000/night", "Rating: 5.0/5", "Central Pandharpur", "Luxury Stay, Spa, Restaurant", "Contact: 9687654321"},
            {"Hotel Govinda", "₹1,100/night", "Rating: 3.7/5", "Railway Road, Pandharpur", "Cozy Rooms, Room Service", "Contact: 9676543210"},
            {"Hotel Yatra", "₹1,600/night", "Rating: 4.0/5", "Near Bhakti Niwas", "Spacious Rooms, Wi-Fi", "Contact: 9665432109"},
            {"Vitthal Darshan Residency", "₹2,700/night", "Rating: 4.5/5", "Prime Location", "AC Rooms, Temple View", "Contact: 9654321098"},
            {"Mandir View Hotel", "₹1,400/night", "Rating: 4.2/5", "Temple Road, Pandharpur", "Pilgrim Stay, Free Parking", "Contact: 9643210987"},
            {"Shankar Lodge", "₹800/night", "Rating: 3.4/5", "New Market Road", "Affordable Rooms, Shared Bathroom", "Contact: 9632109876"},
            {"Hotel Kailash", "₹1,900/night", "Rating: 4.1/5", "Near Highway", "Family Suites, AC", "Contact: 9621098765"},
            {"Green Valley Lodge", "₹1,200/night", "Rating: 3.9/5", "City Outskirts", "Eco-Friendly Stay, Open Garden", "Contact: 9610987654"},
            {"Shirdi Sai Bhavan", "₹1,500/night", "Rating: 4.0/5", "Near Bhakta Niwas", "Spiritual Retreat, Pure Veg", "Contact: 9609876543"},
            {"Shree Krishna Residency", "₹2,300/night", "Rating: 4.4/5", "Main Square", "Luxury Stay, Free Buffet", "Contact: 9598765432"}
    };

    public Accommodation(String username) {
        this.username = username;
        setTitle("Explore Pandharpur - Accommodation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchBtn = new JButton("Search");
        refreshBtn = new JButton("Refresh");

        searchBtn.addActionListener(e -> loadAccommodations(searchField.getText().toLowerCase()));
        refreshBtn.addActionListener(e -> {
            searchField.setText(""); // Clear search field
            loadAccommodations(""); // Reload all accommodations
        });

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);
        add(searchPanel, BorderLayout.NORTH);

        // Accommodation Panel
        accommodationPanel = new JPanel();
        accommodationPanel.setLayout(new BoxLayout(accommodationPanel, BoxLayout.Y_AXIS));
        accommodationPanel.setBackground(new Color(245, 245, 245));

        // Scroll Pane
        scrollPane = new JScrollPane(accommodationPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button Panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backBtn = new JButton("Back");
        backBtn.setBackground(new Color(220, 20, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            new Dashboard(username);
            dispose();
        });

        backPanel.add(backBtn);
        add(backPanel, BorderLayout.SOUTH);

        loadAccommodations(""); // Load all accommodations initially
        setVisible(true);
    }

    private void loadAccommodations(String query) {
        accommodationPanel.removeAll();
        List<String[]> filteredList = new ArrayList<>();

        for (String[] accommodation : ACCOMMODATIONS) {
            if (accommodation[0].toLowerCase().contains(query) ||
                    accommodation[1].toLowerCase().contains(query) ||
                    accommodation[2].toLowerCase().contains(query)) {
                filteredList.add(accommodation);
            }
        }

        for (String[] accommodation : filteredList) {
            addAccommodation(accommodation);
        }

        accommodationPanel.revalidate();
        accommodationPanel.repaint();
    }

    private void addAccommodation(String[] accommodation) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panel.setBackground(new Color(240, 248, 255));
        panel.setPreferredSize(new Dimension(750, 100));

        JLabel nameLabel = new JLabel(accommodation[0], JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(nameLabel, BorderLayout.NORTH);

        JLabel detailsLabel = new JLabel(accommodation[1] + " | " + accommodation[2], JLabel.CENTER);
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(detailsLabel, BorderLayout.CENTER);

        JLabel contactLabel = new JLabel(accommodation[5], JLabel.CENTER);
        contactLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        contactLabel.setForeground(Color.BLUE);
        contactLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(contactLabel, BorderLayout.SOUTH);

        accommodationPanel.add(panel);
        accommodationPanel.add(Box.createVerticalStrut(10));
    }

    public static void main(String[] args) {
        new Accommodation("Test User");
    }
}
