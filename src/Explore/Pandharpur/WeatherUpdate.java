package Explore.Pandharpur;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherUpdate extends JFrame {
    private JLabel temperatureLabel, humidityLabel, windSpeedLabel;
    private String username;
    private static final String API_KEY = "a5042f2285ca2ee511dae2ba466aabfc";
    private static final String LOCATION = "Pandharpur,IN";

    public WeatherUpdate(String username) {
        this.username = username;

        setTitle("Weather Update - Pandharpur");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255));
        JLabel titleLabel = new JLabel("Pandharpur Weather Update", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Weather information panel
        JPanel weatherPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        weatherPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        weatherPanel.setBackground(new Color(240, 248, 255));

        temperatureLabel = new JLabel("Temperature: -- °C", JLabel.CENTER);
        humidityLabel = new JLabel("Humidity: -- %", JLabel.CENTER);
        windSpeedLabel = new JLabel("Wind Speed: -- m/s", JLabel.CENTER);

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        temperatureLabel.setFont(labelFont);
        humidityLabel.setFont(labelFont);
        windSpeedLabel.setFont(labelFont);

        weatherPanel.add(temperatureLabel);
        weatherPanel.add(humidityLabel);
        weatherPanel.add(windSpeedLabel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(50, 205, 50));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.addActionListener(e -> {
            temperatureLabel.setText("Fetching...");
            humidityLabel.setText("Fetching...");
            windSpeedLabel.setText("Fetching...");
            fetchWeatherData();
        });

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            new Dashboard(username); // Navigate back to the Dashboard
            dispose();
        });

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        // Adding components to the frame
        add(headerPanel, BorderLayout.NORTH);
        add(weatherPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Fetch weather data on startup
        fetchWeatherData();
        setVisible(true);
    }

    private void fetchWeatherData() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                            + LOCATION + "&units=metric&appid=" + API_KEY;
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("API request failed. Response Code: " + conn.getResponseCode());
                    }

                    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                        StringBuilder content = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }

                        JSONObject json = new JSONObject(content.toString());
                        double temperature = json.getJSONObject("main").getDouble("temp");
                        int humidity = json.getJSONObject("main").getInt("humidity");
                        double windSpeed = json.getJSONObject("wind").getDouble("speed");

                        SwingUtilities.invokeLater(() -> {
                            temperatureLabel.setText("Temperature: " + temperature + "°C");
                            humidityLabel.setText("Humidity: " + humidity + "%");
                            windSpeedLabel.setText("Wind Speed: " + windSpeed + " m/s");
                        });
                    }
                    conn.disconnect();

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                            WeatherUpdate.this,
                            "Error fetching weather data: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    ));
                }
                return null;
            }
        }.execute();
    }

    public static void main(String[] args) {
        new WeatherUpdate("Guest");
    }
}
