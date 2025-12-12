package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class PhotoGallery extends JFrame {

    private JLabel imageLabel;
    private JButton backButton;
    private ImageIcon[] images;
    private int currentImageIndex = 0;
    private Timer timer;

    public PhotoGallery(String username) {
        // Frame properties
        setTitle("Photo Gallery");
        setBounds(250, 50, 1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(230, 230, 250)); // Light lavender background
        setLayout(null);

        // Image display label
        imageLabel = new JLabel();
        imageLabel.setBounds(100, 80, 800, 500);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(imageLabel);

        // Back button
        backButton = createButton("Back", 440, 600, "Return to the Dashboard", new Color(220, 20, 60));
        addHoverEffect(backButton, new Color(255, 69, 0)); // Orange Red
        backButton.addActionListener(e -> goBack(username));

        // Load images
        loadImages();

        // Start automatic slideshow
        startSlideshow();

        // Display the first image
        updateImage();
    }

    private JButton createButton(String text, int x, int y, String tooltip, Color background) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 40);
        button.setFocusPainted(false);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        add(button);
        return button;
    }

    private void addHoverEffect(JButton button, Color hoverColor) {
        Color originalColor = button.getBackground();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    private void loadImages() {
        String[] imagePaths = {
                "/Explore/Pandharpur/icon/maharaj.jpg",
                "/Explore/Pandharpur/icon/Wel.jpg",
                "/Explore/Pandharpur/icon/pandharpur.jpg",
                "/Explore/Pandharpur/icon/vitthal_temple.jpg",
                "/Explore/Pandharpur/icon/p123.jpg",
                "/Explore/Pandharpur/icon/p124.jpg",
                "/Explore/Pandharpur/icon/pundalik temple.jpeg",
                "/Explore/Pandharpur/icon/Chandrabhaga River.jpeg",
                "/Explore/Pandharpur/icon/Gopalpur Temple.jpg",
                "/Explore/Pandharpur/icon/ISKCON Temple.jpg",
                "/Explore/Pandharpur/icon/math.jpg",
                "/Explore/Pandharpur/icon/Namdev Payari.jpeg",
                "/Explore/Pandharpur/icon/Pundalik Temple.jpeg",
                "/Explore/Pandharpur/icon/Tulsi Vrindavan.jpg",
                "/Explore/Pandharpur/icon/Shri Gajanan Maharaj Mandir.jpeg",
                "/Explore/Pandharpur/icon/p1.jpeg",
                "/Explore/Pandharpur/icon/p2.jpg",
                "/Explore/Pandharpur/icon/p3.jpeg",
                "/Explore/Pandharpur/icon/p65.jpg",
                "/Explore/Pandharpur/icon/p121.jpeg",
                "/Explore/Pandharpur/icon/p32.jpeg",
                "/Explore/Pandharpur/icon/back.jpg",
                "/Explore/Pandharpur/icon/p12.jpg",
        };

        images = new ImageIcon[imagePaths.length];

        for (int i = 0; i < imagePaths.length; i++) {
            try {
                java.net.URL imgUrl = getClass().getResource(imagePaths[i]);
                if (imgUrl != null) {
                    ImageIcon icon = new ImageIcon(imgUrl);
                    Image scaledImage = icon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
                    images[i] = new ImageIcon(scaledImage);
                } else {
                    images[i] = createPlaceholderImage();
                    System.err.println("Image not found: " + imagePaths[i]);
                }
            } catch (Exception e) {
                images[i] = createPlaceholderImage();
                System.err.println("Error loading image: " + imagePaths[i] + " - " + e.getMessage());
            }
        }
    }

    private ImageIcon createPlaceholderImage() {
        BufferedImage placeholder = new BufferedImage(800, 500, BufferedImage.TYPE_INT_ARGB);
        Graphics g = placeholder.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 800, 500);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Image Not Available", 280, 250);
        g.dispose();
        return new ImageIcon(placeholder);
    }

    private void startSlideshow() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        currentImageIndex = (currentImageIndex + 1) % images.length;
                        updateImage();
                    } catch (Exception e) {
                        System.err.println("Slideshow error: " + e.getMessage());
                    }
                });
            }
        }, 0, 2000); // Change image every 2 seconds
    }

    private void updateImage() {
        if (images.length > 0) {
            imageLabel.setIcon(images[currentImageIndex]);
        }
    }

    private void goBack(String username) {
        if (timer != null) {
            timer.cancel(); // Stop the slideshow
        }
        this.dispose();
        SwingUtilities.invokeLater(() -> new Dashboard(username).setVisible(true));
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PhotoGallery("Guest").setVisible(true));
    }
}