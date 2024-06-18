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
        SwingUtilities.invokeLater(CatClient::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // ... (GUI creation code remains the same)
    }

    private static void launchGame(ActionEvent e) {
        String username = usernameField.getText().trim();
        boolean offlineMode = offlineModeCheckbox.isSelected();

        if (offlineMode && username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a username for offline mode.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String launchUrl = EASYMC_API_URL + "?username=" + username + "&offline=" + offlineMode;

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(launchUrl))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String launchCommand = response.body();
                Runtime.getRuntime().exec(launchCommand);
            } else {
                JOptionPane.showMessageDialog(null, "Error launching Minecraft: " + response.body(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error launching Minecraft: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
