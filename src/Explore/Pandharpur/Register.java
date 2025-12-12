package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Register extends JFrame {
    private JTextField usernameField, mobileField, addressField, securityAnswerField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> genderBox, securityQuestionBox;
    private JLabel registerLabel, switchToLogin, switchToWelcome;

    public Register() {
        setTitle("Explore Pandharpur - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(52, 152, 219), 0, getHeight(), new Color(44, 62, 80));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBounds(0, 0, 500, 700);
        panel.setLayout(null);
        add(panel);

        JLabel title = new JLabel("User Registration", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBounds(50, 30, 400, 40);
        panel.add(title);

        String[] labels = {"Username:", "Mobile:", "Address:", "Gender:", "Password:", "Confirm Password:", "Security Question:", "Answer:"};
        int y = 90;
        JComponent[] fields = new JComponent[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setBounds(50, y, 150, 30);
            panel.add(label);

            if (i == 3) {
                fields[i] = new JComboBox<>(new String[]{"Male", "Female", "Other"});
            } else if (i == 6) {
                fields[i] = new JComboBox<>(new String[]{
                        "What is your pet's name?",
                        "What is your favorite book?",
                        "What is your mother's name?",
                        "What was your first school?",
                        "What is your favorite food?"
                });
            } else if (i == 4 || i == 5) {
                fields[i] = new JPasswordField();
            } else {
                fields[i] = new JTextField();
            }
            fields[i].setBounds(200, y, 220, 30);
            panel.add(fields[i]);
            y += 50;
        }

        usernameField = (JTextField) fields[0];
        mobileField = (JTextField) fields[1];
        addressField = (JTextField) fields[2];
        genderBox = (JComboBox<String>) fields[3];
        passwordField = (JPasswordField) fields[4];
        confirmPasswordField = (JPasswordField) fields[5];
        securityQuestionBox = (JComboBox<String>) fields[6];
        securityAnswerField = (JTextField) fields[7];

        // Register Button
        registerLabel = createClickableLabel("Register", 170, 520, Color.YELLOW);
        panel.add(registerLabel);

        switchToLogin = createClickableLabel("Already a member? Login here", 140, 570, Color.WHITE);
        switchToLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        switchToLogin.setSize(250, 30);
        panel.add(switchToLogin);

//        switchToWelcome = createClickableLabel("Back", 170, 610, Color.red);
//        panel.add(switchToWelcome);

        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                registerUser();
            }
        });
        switchToLogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new LoginPage();
                dispose();
            }
        });

//        switchToWelcome.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                new LoginPage();
//                dispose();
//            }
//        });

        setVisible(true);
    }

    private JLabel createClickableLabel(String text, int x, int y, Color color) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBounds(x, y, 180, 30);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return label;
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String mobile = mobileField.getText().trim();
        String address = addressField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String securityQuestion = (String) securityQuestionBox.getSelectedItem();
        String securityAnswer = securityAnswerField.getText().trim();

        if (username.isEmpty() || mobile.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || securityAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!mobile.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Invalid mobile number! Must be 10 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/city", "root", "1234")) {
            PreparedStatement checkStmt = con.prepareStatement("SELECT COUNT(*) FROM users WHERE username=?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Username already taken!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Store plain text password (NOT recommended for security)
            PreparedStatement pst = con.prepareStatement("INSERT INTO users (username, mobile, address, gender, password, security_question, security_answer) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, username);
            pst.setString(2, mobile);
            pst.setString(3, address);
            pst.setString(4, gender);
            pst.setString(5, password); // Storing plain text password
            pst.setString(6, securityQuestion);
            pst.setString(7, securityAnswer);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration Successful!");
            new LoginPage();
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
