package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CityQuiz extends JFrame {

    private int currentQuestionIndex = 0;
    private int score = 0;
    private String username;

    // JDBC connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/city";
    private final String DB_USER = "root"; // change if needed
    private final String DB_PASSWORD = "1234"; // change if needed

    private final String[][] questions = {
            {"What is Pandharpur known as?", "IT Hub", "Industrial Area", "Spiritual City", "Agricultural Hub", "3"},
            {"Which river flows through Pandharpur?", "Godavari", "Krishna", "Chandrabhaga", "Mahanadi", "3"},
            {"Who is the main deity worshipped in Pandharpur?", "Lord Shiva", "Lord Vitthal", "Lord Krishna", "Lord Ganesha", "2"},
            {"Which festival attracts the largest number of pilgrims to Pandharpur?", "Diwali", "Ashadi Ekadashi", "Holi", "Ganesh Chaturthi", "2"},
            {"Which temple is the most famous in Pandharpur?", "Kashi Vishwanath", "Vitthal-Rukmini Temple", "Meenakshi Temple", "Jagannath Temple", "2"},
            {"What is the name of the annual pilgrimage to Pandharpur?", "Ratha Yatra", "Pandharpur Wari", "Kumbh Mela", "Chhath Puja", "2"},
            {"Which saint is closely associated with Pandharpur?", "Sant Dnyaneshwar", "Sant Tukaram", "Sant Eknath", "Sant Namdev", "4"},
            {"What is the traditional procession of Warkaris called?", "Rath Yatra", "Dindi", "Ganga Snan", "Mela", "2"},
            {"Which neighboring city is closest to Pandharpur?", "Solapur", "Pune", "Nagpur", "Nashik", "1"},
            {"How often does the major Ashadi Ekadashi pilgrimage occur?", "Every month", "Once a year", "Every six months", "Once in five years", "2"}
    };

    // UI components
    private JLabel questionLabel;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionsGroup;
    private JButton nextButton, previousButton, submitButton;

    public CityQuiz(String username) {
        this.username = username;

        setBounds(300, 100, 900, 600);
        setTitle("City Quiz");
        getContentPane().setBackground(new Color(230, 230, 250));
        setLayout(null);

        JLabel title = new JLabel("Welcome to the City Quiz, " + username + "!");
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        title.setBounds(200, 20, 600, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        questionLabel.setBounds(50, 100, 800, 30);
        add(questionLabel);

        option1 = createRadioButton(150);
        option2 = createRadioButton(200);
        option3 = createRadioButton(250);
        option4 = createRadioButton(300);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);

        add(option1);
        add(option2);
        add(option3);
        add(option4);

        previousButton = createButton("Previous", 200, 400, new Color(255, 165, 0));
        previousButton.addActionListener(e -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });
        add(previousButton);

        nextButton = createButton("Next", 400, 400, new Color(70, 130, 180));
        nextButton.addActionListener(e -> {
            checkAnswer();
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                loadQuestion();
            } else {
                showResult();
            }
        });
        add(nextButton);

        submitButton = createButton("Submit", 600, 400, new Color(34, 139, 34));
        submitButton.addActionListener(e -> showResult());
        add(submitButton);

        JButton backButton = createButton("Back", 400, 500, new Color(220, 20, 60));
        backButton.addActionListener(e -> {
            this.dispose();
            new Dashboard(username).setVisible(true);
        });
        add(backButton);

        loadQuestion();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JRadioButton createRadioButton(int yPosition) {
        JRadioButton radioButton = new JRadioButton();
        radioButton.setBounds(100, yPosition, 700, 30);
        radioButton.setBackground(new Color(230, 230, 250));
        radioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        return radioButton;
    }

    private JButton createButton(String text, int x, int y, Color bgColor) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 100, 30);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadQuestion() {
        String[] currentQuestion = questions[currentQuestionIndex];
        questionLabel.setText((currentQuestionIndex + 1) + ". " + currentQuestion[0]);
        option1.setText(currentQuestion[1]);
        option2.setText(currentQuestion[2]);
        option3.setText(currentQuestion[3]);
        option4.setText(currentQuestion[4]);
        optionsGroup.clearSelection();
    }

    private void checkAnswer() {
        String correctAnswer = questions[currentQuestionIndex][5];
        if ((option1.isSelected() && correctAnswer.equals("1")) ||
                (option2.isSelected() && correctAnswer.equals("2")) ||
                (option3.isSelected() && correctAnswer.equals("3")) ||
                (option4.isSelected() && correctAnswer.equals("4"))) {
            score++;
        }
    }

    private void showResult() {
        saveResultToDatabase(); // Save result in DB
        JOptionPane.showMessageDialog(this,
                "Quiz Completed!\nYour Score: " + score + "/" + questions.length,
                "Quiz Result",
                JOptionPane.INFORMATION_MESSAGE);

        this.dispose();
        new Dashboard(username).setVisible(true);
    }

    private void saveResultToDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO quiz_results (username, score) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, username);
                stmt.setInt(2, score);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CityQuiz("Guest").setVisible(true);
    }
}
