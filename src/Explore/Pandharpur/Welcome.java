package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.*;

public class Welcome extends JFrame {

    public Welcome() { 
        setTitle("Explore Pandharpur - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        playAudio("src/Explore/Pandharpur/icon/AAA.wav");

        // Background Panel with Image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/Explore/Pandharpur/icon/Wel.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, 900, 600);
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

//        // Title Label
//        JLabel title = new JLabel("Welcome to Pandharpur", JLabel.CENTER);
//        title.setFont(new Font("Serif", Font.BOLD, 30));
//        title.setForeground(Color.WHITE);
//        title.setBounds(200, 30, 500, 50);
//        backgroundPanel.add(title);


        Timer timer = new Timer(5000, e -> {      //  5 seconds
            SwingUtilities.invokeLater(() -> {
                new LoginPage();
                dispose();
            });
        });
        timer.setRepeats(false);
        timer.start();


        setVisible(true);
    }
    private void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Welcome();
    }
}
