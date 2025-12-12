package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Feedback extends JFrame {
    private JTextField nameField, emailField;
    private JTextArea feedbackArea;
    private String username;

    public Feedback(String username) {
        this.username = username;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Feedback Page");
        setBounds(300, 100, 900, 600);
        setLayout(new BorderLayout());

        // Gradient Panel
        JPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(null);
        add(gradientPanel, BorderLayout.CENTER);

        addTitleLabel(gradientPanel);
        addFormFields(gradientPanel);
        addButtons(gradientPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void addTitleLabel(JPanel panel) {
        JLabel title = new JLabel("We value your feedback, " + username + "!");
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setBounds(150, 30, 600, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(new Color(255, 255, 255));
        panel.add(title);
    }

    private void addFormFields(JPanel panel) {
        JPanel formPanel = new JPanel();
        formPanel.setBounds(200, 100, 500, 350);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 220));
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));

        nameField = createTextField("Name:", 30, formPanel);
        emailField = createTextField("Email:", 90, formPanel);
        feedbackArea = createTextArea("Feedback:", 150, formPanel);

        panel.add(formPanel);
    }

    private JTextField createTextField(String labelText, int yPosition, JPanel panel) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(20, yPosition, 100, 30);
        label.setForeground(new Color(70, 70, 70));
        panel.add(label);

        RoundedTextField textField = new RoundedTextField();
        textField.setBounds(130, yPosition, 320, 30);
        panel.add(textField);
        return textField;
    }

    private JTextArea createTextArea(String labelText, int yPosition, JPanel panel) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(20, yPosition, 100, 30);
        label.setForeground(new Color(70, 70, 70));
        panel.add(label);

        RoundedTextArea textArea = new RoundedTextArea();
        textArea.setBounds(130, yPosition, 320, 100);
        panel.add(textArea);
        return textArea;
    }

    private void addButtons(JPanel panel) {
        JButton submitButton = createButton("Submit", 300, 500, new Color(60, 179, 113), new Color(34, 139, 34));
        submitButton.addActionListener(this::handleSubmit);
        panel.add(submitButton);

        JButton backButton = createButton("Back", 450, 500, new Color(255, 69, 0), new Color(139, 0, 0));
        backButton.addActionListener(e -> handleBack());
        panel.add(backButton);
    }

    private JButton createButton(String text, int x, int y, Color bgColor, Color borderColor) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 40);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(borderColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void handleSubmit(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String feedback = feedbackArea.getText().trim();

        if (name.isEmpty() || email.isEmpty() || feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (saveFeedbackToDatabase(name, email, feedback)) {
                JOptionPane.showMessageDialog(this, "Thank you for your feedback!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the Feedback form
                new Dashboard(username).setVisible(true); // Open the Dashboard page
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit feedback. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        feedbackArea.setText("");
    }

    private void handleBack() {
        dispose();
        new Dashboard(username).setVisible(true);
    }

    private boolean saveFeedbackToDatabase(String name, String email, String feedback) {
        String url = "jdbc:mysql://localhost:3306/city";
        String user = "root";
        String password = "1234";
        String query = "INSERT INTO feedback (name, email, feedback) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, feedback);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Feedback("Guest").setVisible(true));
    }

    // Gradient background panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(72, 61, 139), getWidth(), getHeight(), new Color(123, 104, 238));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Rounded TextField
    class RoundedTextField extends JTextField {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(Color.WHITE);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // Rounded TextArea
    class RoundedTextArea extends JTextArea {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(Color.WHITE);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            super.paintComponent(g);
            g2.dispose();
        }
    }
}
