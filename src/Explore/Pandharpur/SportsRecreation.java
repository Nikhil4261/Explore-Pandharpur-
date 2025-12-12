package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SportsRecreation extends JFrame {
    private String username;
    private JPanel sportsPanel;
    private JScrollPane scrollPane;
    private JButton backBtn, refreshBtn;
    private JTextField searchField;

    private static final String[][] SPORTS_DATA = {
            {"Pandharpur Stadium", "Pandharpur Stadium is a popular sports venue featuring cricket and football facilities. \n" +
                    "It hosts local tournaments, school sports events, and fitness activities. \n" +
                    "The stadium offers a well-maintained pitch, football ground, and seating for spectators. \n" +
                    "Athletic training sessions and open jogging tracks are available. \n" +
                    "Operational from 6:00 AM to 8:00 PM, with free entry for morning joggers. \n" +
                    "Paid access is required for tournaments and special events. \n" +
                    "Located near Pandharpur Bus Stand, it is easily accessible. \n" +
                    "Occasionally used for marathons and community fitness programs. \n" +
                    "Ideal for sports enthusiasts looking for professional training. \n" +
                    "For inquiries", "Contact: +91-1234567890"},
            {"Matoshri Sports And Creations", "Matoshri Sports And Creations is a well-known sporting goods store in Pandharpur. \n" +
                    "It offers a wide range of sports equipment, apparel, and accessories. \n" +
                    "Products include cricket bats, footballs, badminton rackets, gym gear, and more. \n" +
                    "Brands like Yonex, SG, Cosco, and Nivia are available. \n" +
                    "The store caters to both beginners and professional athletes. \n" +
                    "Custom sports kits and team uniforms can be ordered. \n" +
                    "Located in the heart of Pandharpur, ensuring easy accessibility. \n" +
                    "Friendly staff provides expert advice on selecting the right gear. \n" +
                    "Open daily from 10:00 AM to 9:00 PM. \n" +
                    "For inquiries", "Contact: +91-9970658526"},
            {"Uma Shikshanshastra Mahavidyalaya Sports Facilities", "Uma Shikshanshastra Mahavidyalaya offers well-equipped sports facilities. \n" +
                    "It includes both indoor and outdoor sports areas for various activities. \n" +
                    "Students can access cricket, basketball, athletics, badminton, volleyball, and football. \n" +
                    "The facilities promote physical fitness and team-building skills. \n" +
                    "Regular sports events and inter-college tournaments are organized. \n" +
                    "Coaching sessions are available for students in multiple sports. \n" +
                    "The institute encourages participation in state and national-level competitions. \n" +
                    "A dedicated sports faculty ensures proper training and guidance. \n" +
                    "The campus provides a supportive environment for sports enthusiasts. \n" +
                    "For more details", "Contact: +91-9421040273"},
            {"Karmaveer Bhaurao Patil Mahavidyalaya Sports Complex", "Karmaveer Bhaurao Patil Mahavidyalaya Sports Complex offers extensive sports facilities. \n" +
                    "It features spacious playgrounds for various outdoor games like football and cricket. \n" +
                    "An indoor multipurpose sports hall is available for table tennis, badminton, and basketball. \n" +
                    "A well-equipped gymnasium supports strength training and fitness activities. \n" +
                    "The complex hosts inter-college and state-level sports tournaments. \n" +
                    "Coaching programs are available for students in different sports disciplines. \n" +
                    "A dedicated team of trainers ensures professional guidance. \n" +
                    "The sports complex encourages holistic development through physical activities. \n" +
                    "It is open to students and selected sports events for the local community. \n" +
                    "For more details", "Contact: N/A"},
            {"Fitness World Gym", "Fitness World Gym is a modern fitness center equipped with advanced workout machines. \n" +
                    "It offers strength training, cardio workouts, and functional fitness programs. \n" +
                    "Personal training sessions are available for customized fitness plans. \n" +
                    "Group fitness classes include yoga, Zumba, and HIIT workouts. \n" +
                    "The gym provides diet and nutrition guidance for members. \n" +
                    "It has a spacious and well-ventilated workout area. \n" +
                    "Specialized fitness programs are designed for beginners and athletes. \n" +
                    "Locker rooms and shower facilities ensure convenience for members. \n" +
                    "Open daily from early morning to late evening. \n" +
                    "For inquiries", "Contact: +91-9876543210"},
            {"Shivaji Sports Club", "Shivaji Sports Club offers multiple sports facilities, including tennis, badminton, and swimming. \n" +
                    "It provides professional coaching for children and adults in various sports. \n" +
                    "The club has a well-maintained swimming pool with lifeguards on duty. \n" +
                    "Badminton courts are equipped with high-quality flooring and lighting. \n" +
                    "Tennis training programs cater to beginners as well as advanced players. \n" +
                    "Regular tournaments and friendly matches are organized for members. \n" +
                    "The club promotes a healthy lifestyle through fitness and recreational activities. \n" +
                    "Membership options are available for individuals and families. \n" +
                    "The facility is open throughout the week with flexible timings. \n" +
                    "For inquiries.", "Contact: +91-1122334455"},
            {"Pandharpur Marathon 2025", "Pandharpur Marathon 2025 is an annual running event promoting health and fitness. \n" +
                    "It aims to spread awareness about environmental sustainability. \n" +
                    "The marathon includes multiple categories: 5K, 10K, Half Marathon, and Full Marathon. \n" +
                    "Participants of all age groups and fitness levels are welcome. \n" +
                    "The event features hydration stations, medical support, and cheering zones. \n" +
                    "Winners and participants receive medals, certificates, and cash prizes. \n" +
                    "The marathon route covers scenic locations across Pandharpur. \n" +
                    "Registrations can be completed online through the official website. \n" +
                    "Corporate and group participation options are available. \n" +
                    "For more details.", "Contact: +91-9988776655"},
            {"Yoga & Meditation Center", "The Yoga & Meditation Center offers daily yoga and guided meditation sessions. \n" +
                    "It focuses on holistic wellness, stress relief, and mindfulness. \n" +
                    "Various yoga styles are practiced, including Hatha, Vinyasa, and Ashtanga. \n" +
                    "Experienced instructors guide both beginners and advanced practitioners. \n" +
                    "Special sessions include breathing techniques (Pranayama) and deep relaxation. \n" +
                    "Wellness retreats and spiritual workshops are organized regularly. \n" +
                    "The center provides a peaceful environment for self-discovery and healing. \n" +
                    "Personalized yoga therapy is available for health and fitness goals. \n" +
                    "Open to all age groups with flexible morning and evening batches. \n" +
                    "For more details.", "Contact: +91-6677889900"},
            {"Pandharpur Cycling Club", "Pandharpur Cycling Club organizes regular cycling events and morning rides. \n" +
                    "It promotes fitness, endurance, and eco-friendly transportation. \n" +
                    "Scenic routes across the city and surrounding countryside are explored. \n" +
                    "Weekend group rides and long-distance cycling challenges are held. \n" +
                    "Cycling enthusiasts of all ages and skill levels are welcome. \n" +
                    "Special training sessions for beginners and professional riders available. \n" +
                    "Safety measures, cycling gear guidance, and maintenance tips provided. \n" +
                    "Annual cycling marathons and awareness rides are conducted. \n" +
                    "A great way to stay active and connect with fellow cyclists. \n" +
                    "For more details.", "Contact: +91-7788991122"},
            {"Boxing Academy", "Boxing Academy offers professional boxing training and self-defense skills. \n" +
                    "Expert coaches provide guidance for beginners to advanced athletes. \n" +
                    "Focuses on strength, endurance, agility, and combat techniques. \n" +
                    "Specialized training programs for kids, teens, and adults. \n" +
                    "Equipped with modern boxing rings, punching bags, and training gear. \n" +
                    "Regular sparring sessions and competitive tournaments are organized. \n" +
                    "Emphasizes discipline, fitness, and mental resilience. \n" +
                    "Women’s self-defense workshops are also available. \n" +
                    "Join to enhance your boxing skills and overall fitness. \n" +
                    "For more details.", "Contact: +91-8899776655"}
    };


    public SportsRecreation(String username) {
        this.username = username;
        setTitle("Explore Pandharpur - Sports & Recreation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel (Search & Refresh)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBackground(new Color(240, 248, 255));
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        refreshBtn = new JButton("Refresh");

        styleButton(searchBtn, new Color(70, 130, 180));
        styleButton(refreshBtn, Color.GREEN);

        searchBtn.addActionListener(e -> searchSports(searchField.getText()));
        refreshBtn.addActionListener(e -> {
            searchField.setText("");
            loadSportsData();
        });

        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        // Main Panel
        sportsPanel = new JPanel();
        sportsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        sportsPanel.setBackground(new Color(240, 248, 255));
        loadSportsData();

        // Scroll Pane
        scrollPane = new JScrollPane(sportsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        backBtn = new JButton("Back");
        styleButton(backBtn, new Color(220, 20, 60));
        backBtn.addActionListener(e -> {
            new Dashboard(username);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(240, 248, 255));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadSportsData() {
        sportsPanel.removeAll();
        for (String[] sports : SPORTS_DATA) {
            addSportsEntry(sports[0], sports[1], sports[2]);
        }
        sportsPanel.revalidate();
        sportsPanel.repaint();
    }

    private void searchSports(String query) {
        sportsPanel.removeAll();
        for (String[] sports : SPORTS_DATA) {
            if (sports[0].toLowerCase().contains(query.toLowerCase())) {
                addSportsEntry(sports[0], sports[1], sports[2]);
            }
        }
        sportsPanel.revalidate();
        sportsPanel.repaint();
    }

    private void addSportsEntry(String name, String description, String contact) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(400, 150));

        JLabel titleLabel = new JLabel(name, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(new Color(70, 130, 180));

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);

        JLabel contactLabel = new JLabel(contact, JLabel.CENTER);
        contactLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        contactLabel.setForeground(Color.DARK_GRAY);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descriptionArea, BorderLayout.CENTER);
        card.add(contactLabel, BorderLayout.SOUTH);

        sportsPanel.add(card);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        new SportsRecreation("Test User");
    }
}
