import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatClient {

    private static final String MINECRAFT_LAUNCH_COMMAND = "java -jar MinecraftLauncher.jar";
    private static JTextField usernameField;
    private static JCheckBox offlineModeCheckbox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CatClient::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Cat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // Disable resizing

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Welcome to Cat Client", JLabel.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:", JLabel.CENTER);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameField);

        offlineModeCheckbox = new JCheckBox("Offline Mode");
        offlineModeCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(offlineModeCheckbox);

        JButton launchButton = new JButton("Launch Game");
        launchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        launchButton.addActionListener(e -> launchGame());
        panel.add(launchButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void launchGame() {
        File minecraftLauncher = new File(MINECRAFT_LAUNCH_COMMAND.split(" ")[2]);
        if (minecraftLauncher.exists()) {
            try {
                List<String> launchCommand = new ArrayList<>();
                launchCommand.add("java");
                launchCommand.add("-jar");
                launchCommand.add("MinecraftLauncher.jar");

                if (isOfflineModeEnabled() && !getUsername().isEmpty()) {
                    launchCommand.add("--username");
                    launchCommand.add(getUsername());
                }

                Runtime.getRuntime().exec(launchCommand.toArray(new String[0]));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error launching Minecraft: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            downloadMinecraftLauncher();
        }
    }

    private static String getUsername() {
        return usernameField.getText().trim();
    }

    private static boolean isOfflineModeEnabled() {
        return offlineModeCheckbox.isSelected();
    }

    private static void downloadMinecraftLauncher() {
        // Placeholder for downloading Minecraft launcher
        // Not implemented in this example
    }
}
