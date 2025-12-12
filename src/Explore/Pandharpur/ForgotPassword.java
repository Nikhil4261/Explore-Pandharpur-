package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ForgotPassword extends JFrame implements ActionListener {
    private JTextField usernameField, answerField;
    private JPasswordField newPasswordField, confirmPasswordField;
    private JLabel questionLabel;
    private JButton fetchQuestionBtn, resetBtn, backBtn;
    private String securityAnswer;

    public ForgotPassword() {
        setTitle("Explore Pandharpur - Forgot Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("Forgot Password", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(50, 20, 400, 30);
        add(title);

        JLabel userLabel = createLabel("Username:", 50, 80);
        add(userLabel);

        usernameField = createTextField(180, 80);
        add(usernameField);

        fetchQuestionBtn = createButton("Fetch Question", new Color(40, 167, 69), 180, 120);
        fetchQuestionBtn.addActionListener(e -> fetchSecurityQuestion());
        add(fetchQuestionBtn);

        questionLabel = createLabel("Security Question: N/A", 50, 160);
        add(questionLabel);

        JLabel answerLabel = createLabel("Answer:", 50, 200);
        add(answerLabel);

        answerField = createTextField(180, 200);
        add(answerField);

        JLabel newPassLabel = createLabel("New Password:", 50, 240);
        add(newPassLabel);

        newPasswordField = createPasswordField(180, 240);
        newPasswordField.setEnabled(false);
        add(newPasswordField);

        JLabel confirmPassLabel = createLabel("Confirm Password:", 50, 280);
        add(confirmPassLabel);

        confirmPasswordField = createPasswordField(180, 280);
        confirmPasswordField.setEnabled(false);
        add(confirmPasswordField);

        resetBtn = createButton("Check Answer", new Color(40, 167, 69), 50, 370);
        resetBtn.setEnabled(false);
        backBtn = createButton("Back", new Color(220, 53, 69), 280, 370);

        add(resetBtn);
        add(backBtn);

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 400, 30);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 220, 30);
        return field;
    }

    private JPasswordField createPasswordField(int x, int y) {
        JPasswordField field = new JPasswordField();
        field.setBounds(x, y, 220, 30);
        return field;
    }

    private JButton createButton(String text, Color bgColor, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 35);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        return button;
    }

    private void fetchSecurityQuestion() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username!");
            return;
        }
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/city", "root", "1234");
             PreparedStatement pst = con.prepareStatement("SELECT security_question, security_answer FROM users WHERE username = ?")) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    questionLabel.setText("Security Question: " + rs.getString("security_question"));
                    securityAnswer = rs.getString("security_answer");
                    resetBtn.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Username not found!");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetBtn) {
            resetPassword();
        } else if (e.getSource() == backBtn) {
            new LoginPage();
            dispose();
        }
    }

    private void resetPassword() {
        String username = usernameField.getText().trim();
        String answer = answerField.getText().trim();

        if (username.isEmpty() || answer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        if (!answer.equalsIgnoreCase(securityAnswer)) {
            JOptionPane.showMessageDialog(this, "Incorrect security answer!");
            return;
        }

        // Enable password fields if the answer is correct
        newPasswordField.setEnabled(true);
        confirmPasswordField.setEnabled(true);
        newPasswordField.requestFocus();

        // Ask the user to enter new password
        JOptionPane.showMessageDialog(this, "Correct answer! Now enter a new password.");

        // Wait for user to enter new passwords and confirm
        resetBtn.setText("Update Password");
        resetBtn.removeActionListener(this);
        resetBtn.addActionListener(e -> updatePassword(username));
    }

    private void updatePassword(String username) {
        String newPassword = new String(newPasswordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter and confirm your new password!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/city", "root", "1234");
             PreparedStatement pst = con.prepareStatement("UPDATE users SET password = ? WHERE username = ?")) {
            pst.setString(1, newPassword);
            pst.setString(2, username);
            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Password Updated Successfully!");
                new LoginPage();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update password!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ForgotPassword();
    }
}
