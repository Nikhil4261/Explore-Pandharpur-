package Explore.Pandharpur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class EventsCalendar extends JFrame {
    private String username;
    private JTable eventsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterComboBox;
    private JSpinner fromDateSpinner;
    private JSpinner toDateSpinner;
    private JButton backBtn;

    private boolean isUpdatingFilters = false;

    private static final String[][] EVENTS = {
            {"2025-01-14", "Makar Sankranti", "Kite flying and traditional sweets"},
            {"2025-02-15", "Pandharpur Yatra", "Annual pilgrimage to Pandharpur"},
            {"2025-03-10", "Music Festival", "Live performances by renowned artists"},
            {"2025-04-05", "Marathon", "Citywide running competition"},
            {"2025-04-18", "Temple Anniversary", "Special prayers and celebrations"},
            {"2025-05-01", "Food Fest", "Variety of local and international cuisines"},
            {"2025-06-05", "World Environment Day", "Tree plantation and awareness programs"},
            {"2025-06-21", "International Yoga Day", "Mass yoga sessions across the city"},
            {"2025-07-14", "Independence Parade", "Flag hoisting and cultural programs"},
            {"2025-08-08", "Book Fair", "Exhibition of books from various genres"},
            {"2025-09-10", "Ganesh Chaturthi", "Ganpati idol installation and visarjan"},
            {"2025-10-02", "Gandhi Jayanti", "Tributes and social service activities"},
            {"2025-11-04", "Diwali Festival", "Fireworks, sweets, and celebrations"},
            {"2025-12-25", "Christmas Celebration", "Church services and festive gatherings"}
    };

    public EventsCalendar(String username) {
        this.username = username;
        setTitle("Explore Pandharpur - Events Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(44, 62, 80));

        JLabel titleLabel = new JLabel("Upcoming Events - 2025", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Date", "Event Name", "Detail"};
        tableModel = new DefaultTableModel(columns, 0);
        eventsTable = new JTable(tableModel);
        eventsTable.setFont(new Font("SansSerif", Font.PLAIN, 16));
        eventsTable.setRowHeight(30);
        eventsTable.setBackground(Color.WHITE);
        eventsTable.setGridColor(new Color(189, 195, 199));

        JTableHeader tableHeader = eventsTable.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        tableHeader.setBackground(new Color(41, 128, 185));
        tableHeader.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(eventsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(new Color(236, 240, 241));

        // From Date Spinner
        filterPanel.add(new JLabel("From Date: "));
        fromDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor fromEditor = new JSpinner.DateEditor(fromDateSpinner, "yyyy-MM-dd");
        fromDateSpinner.setEditor(fromEditor);
        try {
            fromDateSpinner.setValue(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01")); // default from date
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterPanel.add(fromDateSpinner);

        // To Date Spinner
        filterPanel.add(new JLabel("To Date: "));
        toDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor toEditor = new JSpinner.DateEditor(toDateSpinner, "yyyy-MM-dd");
        toDateSpinner.setEditor(toEditor);
        try {
            toDateSpinner.setValue(new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-31")); // default to date
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterPanel.add(toDateSpinner);

        // Event Name Filter ComboBox
        filterPanel.add(new JLabel("Filter by Event Name: "));
        filterComboBox = new JComboBox<>();
        filterComboBox.setBackground(Color.WHITE);
        filterPanel.add(filterComboBox);

        // Back button
        backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.setBackground(new Color(231, 76, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.addActionListener(e -> {
            new Dashboard(username);  // Navigate to Dashboard window
            dispose();
        });
        filterPanel.add(backBtn);

        add(filterPanel, BorderLayout.SOUTH);

        // Initialize event name filter options
        updateEventNameFilterOptions("All");

        // Add listeners
        fromDateSpinner.addChangeListener(e -> {
            if (isUpdatingFilters) return;
            isUpdatingFilters = true;
            try {
                filterEvents();
            } finally {
                isUpdatingFilters = false;
            }
        });

        toDateSpinner.addChangeListener(e -> {
            if (isUpdatingFilters) return;
            isUpdatingFilters = true;
            try {
                filterEvents();
            } finally {
                isUpdatingFilters = false;
            }
        });

        filterComboBox.addActionListener(e -> {
            if (isUpdatingFilters) return;
            isUpdatingFilters = true;
            try {
                filterEvents();
            } finally {
                isUpdatingFilters = false;
            }
        });

        filterEvents();

        setVisible(true);
    }

    private void filterEvents() {
        Date fromDate = (Date) fromDateSpinner.getValue();
        Date toDate = (Date) toDateSpinner.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String selectedEvent = (String) filterComboBox.getSelectedItem();

        tableModel.setRowCount(0);

        boolean todayEventShown = false;
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (String[] event : EVENTS) {
            try {
                Date eventDate = sdf.parse(event[0]);
                boolean inRange = !eventDate.before(fromDate) && !eventDate.after(toDate);
                boolean matchesName = selectedEvent == null || selectedEvent.equals("All") || event[1].equals(selectedEvent);

                if (inRange && matchesName) {
                    tableModel.addRow(event);

                    if (!todayEventShown && event[0].equals(currentDate)) {
                        JOptionPane.showMessageDialog(this,
                                "Today's Event: " + event[1] + " - " + event[2],
                                "Current Event",
                                JOptionPane.INFORMATION_MESSAGE);
                        todayEventShown = true;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateEventNameFilterOptions(String filterDate) {
        Set<String> eventNameSet = new TreeSet<>();
        for (String[] event : EVENTS) {
            eventNameSet.add(event[1]);
        }
        String[] eventNamesWithAll = new String[eventNameSet.size() + 1];
        eventNamesWithAll[0] = "All";
        int i = 1;
        for (String eventName : eventNameSet) {
            eventNamesWithAll[i++] = eventName;
        }
        filterComboBox.setModel(new DefaultComboBoxModel<>(eventNamesWithAll));
        filterComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventsCalendar("Test User"));
    }
}
