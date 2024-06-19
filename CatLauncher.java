import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CatClient {
    private static final String EASYMC_API_URL = "https://api.easymc.io/v1/launcher";
    private static JTextField usernameField;
    private static JCheckBox offlineModeCheckbox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and show GUI
            JFrame frame = new JFrame("Cat Launcher");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 150);
            frame.setLayout(new FlowLayout());

            JLabel usernameLabel = new JLabel("Username:");
            usernameField = new JTextField(20);
            offlineModeCheckbox = new JCheckBox("Offline Mode");
            JButton launchButton = new JButton("Launch Minecraft");

            launchButton.addActionListener(e -> {
                String username = usernameField.getText().trim();
                boolean offlineMode = offlineModeCheckbox.isSelected();

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a username.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String launchCommand = buildLaunchCommand(username, offlineMode);
                    Runtime.getRuntime().exec(launchCommand);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error launching Minecraft: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            frame.add(usernameLabel);
            frame.add(usernameField);
            frame.add(offlineModeCheckbox);
            frame.add(launchButton);

            frame.setVisible(true);
        });
    }

    private static String buildLaunchCommand(String username, boolean offlineMode) throws IOException {
        String launchUrl = EASYMC_API_URL + "?username=" + username + "&offline=" + offlineMode;

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(launchUrl))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Error getting launch command: " + response.body());
        }

        return response.body();
    }
}
