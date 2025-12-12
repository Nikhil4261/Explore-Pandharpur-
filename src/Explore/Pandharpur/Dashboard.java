package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Dashboard extends JFrame implements ActionListener {
    private final String username;
    private final JButton[] buttons;
    private final JButton backBtn;

    public Dashboard(String username) {
        this.username = username;

        // Frame Setup
        setTitle("Explore Pandharpur - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(25, 118, 210)); // Deep Blue
        JLabel title = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Button Panel (Left Side with Gradient)
        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(33, 150, 243), 0, getHeight(), new Color(13, 71, 161));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        buttonPanel.setLayout(new GridLayout(9, 2, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(400, 0));

        // List of Page Names
        String[] pages = {
                "About Pandharpur", "Attractions", "Events Calendar",
                "Transportation Guide", "Weather Update", "Food & Restaurants",
                "City News", "Education & Institutions", "Healthcare Services",
                "Shopping & Malls", "Sports & Recreation", "Safety & Emergency Info",
                "Accommodation", "City Map", "Photo Gallery",
                "City Quiz", "Villages", "Feedback"
        };

        buttons = new JButton[pages.length];

        // Adding Buttons with Hover Effects
        for (int i = 0; i < pages.length; i++) {
            buttons[i] = new JButton(pages[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
            buttons[i].setBackground(new Color(255, 193, 7)); // Gold
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setFocusPainted(false);
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            buttons[i].addActionListener(this);

            // Button Hover Effect
            JButton currentButton = buttons[i];
            currentButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    currentButton.setBackground(new Color(255, 111, 0)); // Orange
                }
                public void mouseExited(MouseEvent evt) {
                    currentButton.setBackground(new Color(255, 193, 7)); // Back to Gold
                }
            });

            buttonPanel.add(buttons[i]);
        }

        add(buttonPanel, BorderLayout.WEST);

        // Right Panel with Image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        String imagePath = "src/Explore/Pandharpur/icon/Pandharpur.jpg";
        File imgFile = new File(imagePath);

        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setText("Image Not Found");
            imageLabel.setFont(new Font("Arial", Font.BOLD, 18));
            imageLabel.setForeground(Color.RED);
        }

        add(imageLabel, BorderLayout.CENTER);

        // Back Button (South)
        backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setBackground(Color.RED);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new LoginPage();
                dispose();
            });
        });

        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.WHITE);
        backPanel.add(backBtn);
        add(backPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : buttons) {
            if (e.getSource() == button) {
                openPage(button.getText());
                break;
            }
        }
    }

    private void openPage(String pageName) {
        dispose();

        try {
            switch (pageName) {
                case "About Pandharpur": new About(username).setVisible(true); break;
                case "Attractions": new Attractions(username).setVisible(true); break;
                case "Events Calendar": new EventsCalendar(username).setVisible(true); break;
                case "Transportation Guide": new TransportationGuide(username).setVisible(true); break;
                case "Weather Update": new WeatherUpdate(username).setVisible(true); break;
                case "Food & Restaurants": new FoodRestaurants(username).setVisible(true); break;
                case "City News": new CityNews(username).setVisible(true); break;
                case "Education & Institutions": new EducationInstitutions(username).setVisible(true); break;
                case "Healthcare Services": new HealthcareServices(username).setVisible(true); break;
                case "Shopping & Malls": new ShoppingMalls(username).setVisible(true); break;
                case "Sports & Recreation": new SportsRecreation(username).setVisible(true); break;
                case "Safety & Emergency Info": new SafetyEmergency(username).setVisible(true); break;
                case "Accommodation": new Accommodation(username).setVisible(true); break;
                case "City Map": new CityMap(username).setVisible(true); break;
                case "Photo Gallery": new PhotoGallery(username).setVisible(true); break;
                case "City Quiz": new CityQuiz(username).setVisible(true); break;
                case "Villages": new Villages(username).setVisible(true); break;
                case "Feedback": new Feedback(username).setVisible(true); break;
                default:
                    JOptionPane.showMessageDialog(this, "Page not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    new Dashboard(username);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "This feature is under development.", "Info", JOptionPane.INFORMATION_MESSAGE);
            new Dashboard(username);
        }
    }

    public static void main(String[] args) {
        new Dashboard("Test User");
    }
}
