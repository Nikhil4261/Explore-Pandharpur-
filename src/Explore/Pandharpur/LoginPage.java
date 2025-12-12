package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, signupBtn, forgotPassBtn, backBtn, showPassBtn;
    private JLabel guestLabel;
    private boolean isPasswordVisible = false;

    public LoginPage() {
        setTitle("Explore Pandharpur - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500); // Increased size
        setLocationRelativeTo(null);
        setLayout(null);

        // Gradient Panel
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(30, 58, 138),
                        getWidth(), getHeight(), new Color(96, 165, 250));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);
        panel.setBounds(0, 0, 450, 550);
        add(panel);

        // Title
        JLabel title = new JLabel("Login Form", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(125, 30, 200, 30);
        panel.add(title);

        // Username Field
        JLabel userLabel = new JLabel("UserName:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(75, 80, 300, 20);
        panel.add(userLabel);
        usernameField = new JTextField();
        usernameField.setBounds(75, 100, 250, 40);
        styleTextField(usernameField);
        panel.add(usernameField);

        // Password Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(75, 150, 300, 20);
        panel.add(passLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(75, 170, 210, 40);
        styleTextField(passwordField);
        panel.add(passwordField);

        // Show Password Button
        showPassBtn = new JButton(" 👁 ");
        showPassBtn.setBounds(290, 170, 35, 40);
        showPassBtn.setFocusPainted(false);
        showPassBtn.addActionListener(e -> togglePasswordVisibility());
        panel.add(showPassBtn);

        // Forgot Password Button
        forgotPassBtn = new JButton("Forgot Password?");
        forgotPassBtn.setBounds(75, 220, 250, 30);
        forgotPassBtn.setForeground(Color.WHITE);
        forgotPassBtn.setContentAreaFilled(false);
        forgotPassBtn.setBorderPainted(false);
        forgotPassBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(forgotPassBtn);

        // Login Button
        loginBtn = new JButton("Login");
        styleButton(loginBtn, false);
        panel.add(loginBtn);

        // Signup Button (new, same style as Login)
        signupBtn = new JButton("Signup");
        styleButton(signupBtn, false);
        panel.add(signupBtn);

        // Set bounds for login and signup buttons side by side
        loginBtn.setBounds(75, 270, 115, 40);
        signupBtn.setBounds(210, 270, 115, 40);

        // Guest Login Label
        guestLabel = new JLabel("Continue as Guest", JLabel.CENTER);
        guestLabel.setForeground(Color.WHITE);
        guestLabel.setBounds(100, 320, 200, 30);
        guestLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(guestLabel);

        // Back Button
        backBtn = new JButton("Back");
        styleButton(backBtn, true);
        backBtn.setBounds(75, 370, 250, 40);
        panel.add(backBtn);

        // Action Listeners
        loginBtn.addActionListener(this);
        signupBtn.addActionListener(e -> {
            new Register();
            dispose();
        });
        forgotPassBtn.addActionListener(this);
        backBtn.addActionListener(this);

        guestLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Logged in as Guest");
                new Dashboard("Guest");
                dispose();
            }
        });

        setVisible(true);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleButton(JButton button, boolean isBackButton) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isBackButton) {
            button.setBackground(new Color(220, 38, 38)); // Red for Back
        } else {
            button.setBackground(new Color(37, 99, 235)); // Deep Blue for Login and Signup
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordField.setEchoChar('*');
            showPassBtn.setText("👁");
        } else {
            passwordField.setEchoChar((char) 0);
            showPassBtn.setText("👁‍🗨");
        }
        isPasswordVisible = !isPasswordVisible;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            login();
        } else if (e.getSource() == forgotPassBtn) {
            new ForgotPassword();
            dispose();
        } else if (e.getSource() == backBtn) {
            new Welcome();
            dispose();
        }
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/city", "root", "1234");
             PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    new Dashboard(username);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
