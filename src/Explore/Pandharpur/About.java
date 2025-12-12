package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JFrame {
    private JButton backButton;
    private String username;

    public About(String username) {
        this.username = username;
        setTitle("About Pandharpur - Explore the City");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 20)); // Dark background

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 30, 30));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        JLabel titleLabel = new JLabel("About Pandharpur");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 215, 0));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Text Area
        JTextArea aboutText = new JTextArea(getCityDescription());
        aboutText.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutText.setWrapStyleWord(true);
        aboutText.setLineWrap(true);
        aboutText.setEditable(false);
        aboutText.setBackground(new Color(40, 40, 40));
        aboutText.setForeground(Color.WHITE);
        aboutText.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Scroll Pane with Custom Styling
        JScrollPane scrollPane = new JScrollPane(aboutText);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

       // Create Back Button
        backButton = createStyledButton("Back", new Color(220, 53, 69), Color.WHITE);
        backButton.setPreferredSize(new Dimension(80, 30)); // Reduced size

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard(username);
                dispose();
            }
        });

        buttonPanel.add(backButton);


        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setPreferredSize(new Dimension(140, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private String getCityDescription() {
        return "Pandharpur, located in Maharashtra, is a renowned pilgrimage town situated on the banks of the Chandrabhaga River.\n\n"
                + "Pandharpur, often referred to as the \"Southern Kashi of India,\" is a revered pilgrimage town located in the Solapur district of Maharashtra. Situated on the banks of the Chandrabhaga River, the town is renowned for the Shri Vitthal-Rukmini Temple, attracting millions of devotees annually.\n\n"
                + "It is best known for the Vithoba Temple, officially known as Shri Vitthal-Rukmini Mandir, attracting millions of devotees, especially during Ashadhi and Kartiki Ekadashi Yatras. "
                + "The temple was built by King Vishnuvardhana of the Hoysala Empire between 1108 and 1152 CE. In 2014, it became India's first temple to appoint women and people from backward classes as priests.\n\n"
                + "The town has strong connections with the Bhakti movement and saints like Sant Tukaram, Sant Dnyaneshwar, and Sant Namdev. The famous 'Sant Namdev Maharaj Pāyari' step signifies devotion where God himself accepted a child devotee’s offering.\n\n"
                + "Historically, the town dates back to the Rashtrakuta era (516 CE). The Yadava Kings, Adilshahi rulers, and Marathas contributed to its development. Despite being attacked by Afzal Khan, Pandharpur remained a spiritual and social-reform center, influencing the Maratha Empire.\n\n"
                + "Today, Pandharpur is known for its grand kirtans, religious festivals, and warm hospitality. Apart from spirituality, it offers delicious local foods, scenic ghats, and improving urban infrastructure. "
                + "It is well connected by road and rail, making it accessible from cities like Pune, Solapur, and Mumbai."
                + "The town's prominence stems from the Vithoba temple, dedicated to Lord Vitthal (a form of Krishna) and his consort Rukmini. This temple serves as a central hub for the Bhakti movement in Maharashtra, with saints like Dnyaneshwar, Tukaram, and Namdev having deep associations with Pandharpur. The temple complex is a focal point during major pilgrimages, especially the Ashadhi and Kartiki yatras, drawing devotees from across Maharashtra, Karnataka, and parts of Tamil Nadu. \n\n"
                + "Historical records trace Pandharpur's origins back to 516 CE during the Rashtrakuta era. The town has witnessed various dynastic influences, including the Yadavas in the 11th and 12th centuries, who made significant contributions to the temple's development. Despite challenges, such as destruction during the Adilshahi period, Pandharpur emerged as a resilient center of the devotional movement, laying the foundation for the Maratha Empire. \n\n"
                + "Pandharpur hosts four major annual yatras: Chaitri, Ashadhi, Kartiki, and Maghi. Among these, the Ashadhi and Kartiki yatras are the most significant, attracting vast numbers of devotees who undertake the Wari pilgrimage, a tradition that has been preserved for centuries.\n\n"
                + "The Shri Vitthal-Rukmini Temple stands as a testament to the town's architectural and spiritual heritage. The temple's chief gate faces the Bhima River, leading to the Mahadwar ghat, where devotees perform various rituals. The temple complex, rebuilt under the patronage of the Peshwas, Scindias, and Holkars in the 18th century, showcases intricate designs and holds immense historical significance. \n\n";
    }

    // Custom ScrollBar UI
    static class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(100, 100, 100);
        }
    }

    public static void main(String[] args) {
        new About("TestUser");
    }
}
