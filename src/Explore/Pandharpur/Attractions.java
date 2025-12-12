package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Attractions extends JFrame {
    private String username;
    private JPanel attractionsPanel;
    private JScrollPane scrollPane;
    private JButton backBtn, toggleThemeBtn;
    private boolean isDarkMode = false;

    // Hardcoded list of attractions
    private static final String[][] ATTRACTIONS = {
            {"Chhatrapati Shivaji Maharaj Chowk", "Chhatrapati Shivaji Maharaj Chowk in Pandharpur is a prominent landmark featuring a majestic statue of Shivaji Maharaj on horseback. It serves as a key junction connecting major roads and is a center for cultural events, especially during Shiv Jayanti. The chowk symbolizes Shivaji Maharaj’s legacy of bravery and leadership, making it a place of pride and inspiration for locals.", "src/Explore/Pandharpur/icon/maharaj.jpg"},
            {"Vitthal Rukmini Temple", "The Vitthal Rukmini Temple in Pandharpur is a revered Hindu temple dedicated to Lord Vitthal (Vithoba) and Goddess Rukmini. It is a major pilgrimage site, especially during Ashadhi Ekadashi, attracting millions of devotees. The temple is known for its unique tradition where devotees can touch the idol's feet, symbolizing a deep spiritual connection with the deity.", "src/Explore/Pandharpur/icon/vitthal_temple.jpg"},
            {"Pundalik Temple", "The Pundalik Temple in Pandharpur is dedicated to Saint Pundalik, a great devotee of Lord Vitthal. It is believed to be the place where Pundalik attained divine blessings, leading to the manifestation of Lord Vitthal in Pandharpur. Situated on the banks of the Chandrabhaga River, the temple holds immense spiritual significance and attracts devotees seeking blessings and peace.", "src/Explore/Pandharpur/icon/pundalik temple.jpeg"},
            {"Chandrabhaga River", "The Chandrabhaga River in Pandharpur is considered sacred by devotees of Lord Vitthal. It forms a crescent-shaped bend near the town, resembling a half-moon (Chandra), which gives it its name. Pilgrims take holy dips in the river, believing it washes away sins and purifies the soul. The river plays a vital role during Ashadhi Ekadashi, when thousands of devotees gather for the annual pilgrimage.", "src/Explore/Pandharpur/icon/Chandrabhaga River.jpeg"},
            {"ISKCON Temple", "The ISKCON Temple in Pandharpur is a beautiful and serene place dedicated to Lord Krishna. Managed by the International Society for Krishna Consciousness (ISKCON), it serves as a spiritual hub for devotees, offering kirtans, bhajans, and discourses on Krishna's teachings. The temple features intricate architecture, peaceful surroundings, and a divine atmosphere, attracting visitors seeking devotion and tranquility", "src/Explore/Pandharpur/icon/iskcon temple.jpg"},
            {"Namdev Payari", "Namdev Payari is a sacred step at the entrance of the Vitthal Rukmini Temple in Pandharpur. It is named after Sant Namdev, a revered saint and devotee of Lord Vitthal. According to legend, Lord Vitthal himself appeared at this step to bless Namdev, symbolizing divine acceptance of his devotion. Pilgrims consider touching or stepping on Namdev Payari as an act of great spiritual significance.", "src/Explore/Pandharpur/icon/Namdev Payari.jpeg"},
            {"sant kaikadi maharaj math", "The Kaikadi Maharaj Math is a revered spiritual institution dedicated to the renowned saint, Sri Kaikadi Maharaj. Math serves as a center for spiritual teachings, meditation, and religious discourses. One of the key teachings of Kaikadi Maharaj emphasizes the importance of selfless service (seva) and compassion towards all beings.", "src/Explore/Pandharpur/icon/math.jpg"},
            {"Shri Gajanan Maharaj Mandir ", "The Shri Sant Gajanan Maharaj Mandir is a temple in Pandharpur, Maharashtra, India. It's dedicated to Gajanan Maharaj, who is believed to be an avatar of Lord Ganesha", "src/Explore/Pandharpur/icon/Shri Gajanan Maharaj Mandir.jpeg"},
            {"Gopalpur Temple", "Gopalpur Temple is a serene and spiritually significant site located near Pandharpur. It is dedicated to Lord Krishna and is known for its peaceful surroundings, making it an ideal place for meditation and devotion. The temple attracts pilgrims seeking divine blessings and offers a tranquil retreat from the bustling town. Many devotees visit Gopalpur as part of their pilgrimage to Pandharpur.", "src/Explore/Pandharpur/icon/Gopalpur Temple.jpg"},
            {"Tulsi Vrindavan", "The Tulasi Vrindavan is a sacred spot in Pandharpur, often found within temple complexes and near devotees' homes. It is a raised platform dedicated to the Tulsi (Holy Basil) plant, considered highly auspicious in Hinduism.", "src/Explore/Pandharpur/icon/Tulsi Vrindavan.jpg"},
    };

    public Attractions(String username) {
        this.username = username;
        setTitle("Explore Pandharpur - Attractions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel for displaying attractions
        attractionsPanel = new JPanel();
        attractionsPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns, dynamic rows
        loadAttractions(); // Load attractions

        // Scroll Pane for scrolling through attractions
        scrollPane = new JScrollPane(attractionsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        backBtn = new JButton("Back");
        styleButton(backBtn, Color.RED);
        backBtn.addActionListener(e -> {
            new Dashboard(username); // Go back to Dashboard
            dispose();
        });

//        // Theme Toggle Button
//        toggleThemeBtn = new JButton("Toggle Theme");
//        styleButton(toggleThemeBtn, new Color(51, 153, 255));
//        toggleThemeBtn.addActionListener(e -> toggleTheme());

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backBtn);
      //  buttonPanel.add(toggleThemeBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadAttractions() {
        for (String[] attraction : ATTRACTIONS) {
            addAttraction(attraction[0], attraction[1], attraction[2]);
        }
    }

    private void addAttraction(String name, String description, String imagePath) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Load Image with Error Handling
        JLabel imageLabel = new JLabel(loadImage(imagePath, 375, 275));
        panel.add(imageLabel, BorderLayout.NORTH);

        // Name Label
        JLabel nameLabel = new JLabel(name, JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(nameLabel, BorderLayout.CENTER);

        // Details Button
        JButton detailsBtn = new JButton("Details");
        styleButton(detailsBtn, new Color(51, 153, 255));
        detailsBtn.addActionListener(e -> showAttractionDetails(name, description, imagePath));
        panel.add(detailsBtn, BorderLayout.SOUTH);

        attractionsPanel.add(panel);
        attractionsPanel.revalidate();
        attractionsPanel.repaint();
    }

    private void showAttractionDetails(String name, String description, String imagePath) {
        JDialog detailsDialog = new JDialog(this, name, true);
        detailsDialog.setSize(600, 400); // Increased dialog size
        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.setLayout(new BorderLayout());

        // Load Image with Increased Size
        JLabel imageLabel = new JLabel(loadImage(imagePath, 500, 250)); // Increased image size
        detailsDialog.add(imageLabel, BorderLayout.NORTH);

        // Description with Larger Font
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Increased font size
        detailsDialog.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14)); // Larger font for button
        closeButton.addActionListener(e -> detailsDialog.dispose());
        detailsDialog.add(closeButton, BorderLayout.SOUTH);

        detailsDialog.setVisible(true);
    }



    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        Color bgColor = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;

        attractionsPanel.setBackground(bgColor);
        attractionsPanel.setForeground(fgColor);

        for (Component comp : attractionsPanel.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(bgColor);
                comp.setForeground(fgColor);
            }
        }

        attractionsPanel.revalidate();
        attractionsPanel.repaint();
    }

    private ImageIcon loadImage(String imagePath, int width, int height) {
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            try {
                BufferedImage img = ImageIO.read(imgFile);
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            } catch (IOException e) {
                System.err.println("Error loading image: " + imagePath);
            }
        } else {
            System.err.println("Image not found: " + imagePath);
        }

        // Fallback Placeholder Image (Ensure this exists in your project)
        return new ImageIcon(new ImageIcon("src/Explore/Pandharpur/icon/placeholder.jpg")
                .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Main Method for Testing
    public static void main(String[] args) {
        new Attractions("Test User");
    }
}
