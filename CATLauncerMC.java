import javax.swing.*;  
import javax.swing.JTextField;
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CatClient  {
    private JFrame frame;  
    private JTextField versionField;  
    private JButton downloadButton, launchButton;  
    private JLabel statusLabel;  

    public static void main(String[] args)  {
        EventQueue.invokeLater(() -> {
            try {
                CatClient window = new CatClient();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CatClient()   {  
        initialize(); 
    }  

    private void initialize()  {  
        frame = new JFrame("Cat Client");  
        frame.setBounds(100, 100, 450, 300);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        versionField = new JTextField();
        versionField.setBounds(10, 10, 86, 20);
        frame.getContentPane().add(versionField);
        versionField.setColumns(10);

        downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener()   {  
            public void actionPerformed(ActionEvent event)  {  
                try  {  
                    URI uri = new URI("https", "api.easymc.io", "/v1/minecraft/download", "version=" + versionField.getText(), null);
                    URL url = uri.toURL();
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("HEAD");
                    int responseCode = con.getResponseCode();  
                    if(responseCode == 200) {  
                        // Download successful
                    } else {
                        // Handle download error
                    }
                } catch(Exception exception) {  
                    exception.printStackTrace();
                }
            }
        });
        downloadButton.setBounds(10, 40, 100, 30);
        frame.getContentPane().add(downloadButton);

        launchButton = new JButton("Launch");
        launchButton.addActionListener(new ActionListener()   {  
            public void actionPerformed(ActionEvent e)  {  
                try  {  
                    URI uri = new URI("https", "sessionserver.easymc.io", "/session/minecraft/join", "version=" + versionField.getText(), null);
                    URL url = uri.toURL();
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("HEAD");
                    int responseCode = con.getResponseCode();  
                    if(responseCode == 200) {  
                        // Launch successful
                    } else {
                        // Handle launch error
                    }
                } catch(Exception exception) {  
                    exception.printStackTrace();
                }
            }
        });
        launchButton.setBounds(10, 80, 100, 30);
        frame.getContentPane().add(launchButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 120, 300, 20);
        frame.getContentPane().add(statusLabel);
    }  
}
