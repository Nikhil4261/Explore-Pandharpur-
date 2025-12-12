package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SafetyEmergency extends JFrame {
    private String username;
    private JPanel emergencyPanel;
    private JScrollPane scrollPane;
    private JButton backBtn, searchBtn, refreshBtn;
    private JTextField searchField;

    // Hardcoded list of emergency contact details
    private static final String[][] EMERGENCY_CONTACTS = {
            {"Police Station", "Dial for emergencies or local police assistance.", "100", "24/7 Service", "Nearest Police Station: Pandharpur City Police Station"},
            {"Ambulance Service", "Available 24/7 for medical emergencies.", "108", "24/7 Service", "Government & Private Ambulance Services"},
            {"Fire Department", "Call for fire and rescue operations.", "101", "24/7 Service", "Fire Station: Pandharpur Fire Brigade"},
            {"Electricity Helpline", "Report power outages or electrical emergencies.", "1912", "9 AM - 9 PM", "Maharashtra State Electricity Board"},
            {"Pandharpur Municipal Corporation", "General assistance and complaints.", "02186-223013", "10 AM - 5 PM (Mon-Sat)", "Email: pandharpurmc@gmail.com"},
            {"District Hospital", "24/7 emergency medical services.", "02186-223181", "24/7 Service", "Services: Emergency, ICU, OPD, Surgery"},
            {"Blood Bank Service", "Donate or request blood for emergencies.", "02186-223147", "24/7 Service", "Blood Groups Available: A, B, AB, O (Positive & Negative)"},
            {"Women’s Helpline", "Support for women in distress.", "1091", "24/7 Service", "Services: Domestic Violence, Legal Aid, Women’s Rights"},
            {"Disaster Management Helpline", "Floods, earthquakes, and natural disasters.", "1070", "24/7 Service", "Maharashtra Disaster Response Team"},
            {"Child Helpline", "Help for children in distress.", "1098", "24/7 Service", "Child Protection & Welfare Services"},
            {"Railway Helpline", "Assistance for train-related emergencies.", "139", "24/7 Service", "Services: Ticket Issues, Lost Items, Medical Emergencies"},
            {"Road Accident Helpline", "Report road accidents and get immediate help.", "1073", "24/7 Service", "Maharashtra Road Safety & Traffic Control"},
            {"Gas Leak Helpline", "Emergency response for gas leaks.", "1906", "24/7 Service", "LPG Gas Suppliers: HP Gas, Bharat Gas, Indane Gas"},
            {"Mental Health Helpline", "Counseling and support for mental health issues.", "9152987821", "9 AM - 9 PM", "Services: Depression, Anxiety, Stress Management"},
            {"Animal Rescue & Welfare", "Report injured or stray animals.", "+91-9876543210", "10 AM - 6 PM", "Animal Shelter & Veterinary Services"},
            {"Cyber Crime Helpline", "Report online fraud, cyber harassment, and scams.", "1930", "24/7 Service", "Website: https://cybercrime.gov.in"},
            {"Tourist Assistance Helpline", "Help for tourists regarding travel and safety.", "+91-9887654321", "9 AM - 7 PM", "Tourism Office, Pandharpur"},
            {"Senior Citizen Helpline", "Support and assistance for elderly citizens.", "14567", "24/7 Service", "Government Welfare for Senior Citizens"},
            {"Suicide Prevention Helpline", "Confidential counseling and emotional support.", "9152987821", "24/7 Service", "Mental Health Foundation"}
    };
    public SafetyEmergency(String username) {
        this.username = username;
        setTitle("Explore Pandharpur - Safety & Emergency Info");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // **Set Background Color**
        getContentPane().setBackground(new Color(230, 242, 255));

        // **Search & Refresh Panel**
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(200, 220, 240));

        searchField = new JTextField(20);
        searchBtn = new JButton("Search");
        refreshBtn = new JButton("Refresh");

        styleButton(searchBtn, new Color(50, 120, 200));
        styleButton(refreshBtn, new Color(50, 160, 120));

        searchBtn.addActionListener(e -> filterContacts(searchField.getText().trim()));
        refreshBtn.addActionListener(e -> {
            searchField.setText("");
            loadEmergencyContacts(""); // Reload all contacts
        });

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);
        add(searchPanel, BorderLayout.NORTH);

        // **Emergency Panel**
        emergencyPanel = new JPanel();
        emergencyPanel.setLayout(new BoxLayout(emergencyPanel, BoxLayout.Y_AXIS));
        emergencyPanel.setBackground(new Color(230, 242, 255));
        loadEmergencyContacts("");

        // **Scroll Pane**
        scrollPane = new JScrollPane(emergencyPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // **Back Button Panel**
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.setBackground(new Color(200, 220, 240));

        backBtn = new JButton("Back");
        styleButton(backBtn, new Color(220, 50, 60));
        backBtn.addActionListener(e -> {
            new Dashboard(username);
            dispose();
        });

        backPanel.add(backBtn);
        add(backPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadEmergencyContacts(String filter) {
        emergencyPanel.removeAll();
        for (String[] contact : EMERGENCY_CONTACTS) {
            if (filter.isEmpty() || contact[0].toLowerCase().contains(filter.toLowerCase())) {
                addEmergencyContact(contact[0], contact[1], contact[2], contact[3], contact[4]);
            }
        }
        emergencyPanel.revalidate();
        emergencyPanel.repaint();
    }

    private void filterContacts(String query) {
        loadEmergencyContacts(query);
    }

    private void addEmergencyContact(String name, String description, String contact, String availability, String additionalInfo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(50, 120, 200), 2));
        panel.setBackground(new Color(245, 250, 255));
        panel.setPreferredSize(new Dimension(750, 100));

        JLabel nameLabel = new JLabel(name, JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(new Color(30, 60, 90));
        panel.add(nameLabel, BorderLayout.NORTH);

        JLabel descriptionLabel = new JLabel("<html><p style='padding:5px;'>" + description + "<br><b>Availability:</b> " + availability + "<br><b>Info:</b> " + additionalInfo + "</p></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(50, 80, 110));
        panel.add(descriptionLabel, BorderLayout.CENTER);

        JLabel contactLabel = new JLabel(contact, JLabel.CENTER);
        contactLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        contactLabel.setForeground(new Color(50, 120, 200));
        contactLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contactLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Dial: " + contact, "Emergency Contact", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(contactLabel, BorderLayout.SOUTH);

        emergencyPanel.add(panel);
        emergencyPanel.add(Box.createVerticalStrut(10));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        new SafetyEmergency("Test User");
    }
}
