import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatClient {

    private static final String LAUNCHER_PATH = "MinecraftLauncher.jar";
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
        frame.setResizable(false);

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
        launchButton.addActionListener(CatClient::launchGame);
        panel.add(launchButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void launchGame(ActionEvent e) {
        File minecraftLauncher = new File(LAUNCHER_PATH);
        if (minecraftLauncher.exists()) {
            try {
                List<String> command = new ArrayList<>();
                command.add("java");
                command.add("-jar");
                command.add(LAUNCHER_PATH);

                if (offlineModeCheckbox.isSelected() && !usernameField.getText().trim().isEmpty()) {
                    command.add("--username");
                    command.add(usernameField.getText().trim());
                }

                new ProcessBuilder(command).start();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error launching Minecraft: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Minecraft launcher file not found.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            downloadMinecraftLauncher();
        }
    }

    private static void downloadMinecraftLauncher() {
        // Placeholder for downloading Minecraft launcher
        JOptionPane.showMessageDialog(null, "Downloading Minecraft launcher is not implemented.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
