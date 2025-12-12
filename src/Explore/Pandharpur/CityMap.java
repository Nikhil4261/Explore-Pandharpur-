package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CityMap extends JFrame {
    private String username;
    private JLabel mapLabel;
    private ImageIcon originalIcon;
    private Image originalImage;
    private int zoomLevel = 100; // Default zoom %
    private JSlider zoomSlider;
    private JPanel imagePanel;

    public CityMap(String username) {
        this.username = username;
        setTitle("Explore Pandharpur - City Map");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(40, 44, 52));

        loadMapImage();
        setupControlPanel();

        setVisible(true);
    }

    // Load and display map image
    private void loadMapImage() {
        try {
            java.net.URL imageUrl = getClass().getResource("/Explore/Pandharpur/icon/Map1.jpg");

            if (imageUrl != null) {
                originalIcon = new ImageIcon(imageUrl);
                originalImage = originalIcon.getImage();

                mapLabel = new JLabel(new ImageIcon(originalImage));
                mapLabel.setHorizontalAlignment(JLabel.CENTER);
                mapLabel.setVerticalAlignment(JLabel.CENTER);

                imagePanel = new JPanel(new GridBagLayout()); // center the image
                imagePanel.setBackground(new Color(40, 44, 52));
                imagePanel.add(mapLabel);

                JScrollPane scrollPane = new JScrollPane(imagePanel);
                scrollPane.getViewport().setBackground(new Color(40, 44, 52));
                add(scrollPane, BorderLayout.CENTER);
            } else {
                mapLabel = new JLabel("⚠ Map image not found!", JLabel.CENTER);
                mapLabel.setFont(new Font("Arial", Font.BOLD, 20));
                mapLabel.setForeground(Color.WHITE);
                add(mapLabel, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    // Create zoom + back controls
    private void setupControlPanel() {
        JPanel controlPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 102, 204),
                        getWidth(), getHeight(), new Color(0, 51, 102));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel zoomText = new JLabel("Zoom:");
        zoomText.setFont(new Font("Arial", Font.BOLD, 14));
        zoomText.setForeground(Color.WHITE);

        zoomSlider = new JSlider(50, 200, zoomLevel);
        zoomSlider.setMajorTickSpacing(50);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setPaintLabels(true);
        zoomSlider.setForeground(Color.WHITE);
        zoomSlider.setBackground(new Color(0, 51, 102));

        zoomSlider.addChangeListener(e -> {
            zoomLevel = zoomSlider.getValue();
            resizeImage();
        });

        // Zoom in and Zoom out buttons
        JButton zoomIn = new JButton("+");
        JButton zoomOut = new JButton("−");
        setupZoomButton(zoomIn, 10);
        setupZoomButton(zoomOut, -10);

        // Fit to screen
        JButton fitButton = new JButton("Fit to Screen");
        fitButton.setBackground(new Color(32, 178, 170));
        fitButton.setForeground(Color.WHITE);
        fitButton.setFont(new Font("Arial", Font.BOLD, 13));
        fitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fitButton.setFocusPainted(false);

        fitButton.addActionListener(e -> fitToScreen());

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        backButton.addActionListener(e -> {
            new Dashboard(username);
            dispose();
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(new Color(178, 0, 40));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(new Color(220, 20, 60));
            }
        });

        controlPanel.add(zoomText);
        controlPanel.add(zoomOut);
        controlPanel.add(zoomSlider);
        controlPanel.add(zoomIn);
        controlPanel.add(fitButton);
        controlPanel.add(backButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    // Resize image based on zoom level
    private void resizeImage() {
        if (originalImage == null) return;

        int newWidth = originalIcon.getIconWidth() * zoomLevel / 100;
        int newHeight = originalIcon.getIconHeight() * zoomLevel / 100;

        Image newImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        mapLabel.setIcon(new ImageIcon(newImage));
        mapLabel.revalidate();
        mapLabel.repaint();
    }

    // Zoom button helper
    private void setupZoomButton(JButton button, int step) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(45, 30));

        button.addActionListener(e -> {
            zoomLevel = Math.max(50, Math.min(zoomLevel + step, 200));
            zoomSlider.setValue(zoomLevel);
        });
    }

    // Fit to screen method
    private void fitToScreen() {
        if (originalImage == null) return;

        Dimension size = getContentPane().getSize();
        int maxW = size.width - 100;
        int maxH = size.height - 150;

        double widthRatio = (double) maxW / originalIcon.getIconWidth();
        double heightRatio = (double) maxH / originalIcon.getIconHeight();
        double scale = Math.min(widthRatio, heightRatio);

        zoomLevel = (int) (scale * 100);
        zoomSlider.setValue(zoomLevel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CityMap("Test User"));
    }
}
