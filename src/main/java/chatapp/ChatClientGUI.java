package chatapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private PrintWriter out;

    public ChatClientGUI() {
        setTitle("Java Chat Client");
        setSize(480, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout(10, 10));

        // Add padding to the entire content pane
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.WHITE);

        // --- Chat Area ---
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        // Use a softer background color
        chatArea.setBackground(new Color(245, 245, 245)); 
        // Add internal padding to the text area
        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10)); 
        chatArea.append("Connected to Chat Server!\nType your message below:\n\n");

        JScrollPane scrollPane = new JScrollPane(chatArea);
        // Remove the default border from the scroll pane
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        add(scrollPane, BorderLayout.CENTER);

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new BorderLayout(40, 10)); // Increased gap
        inputPanel.setBorder(new EmptyBorder(5, 0, 0, 0)); // Add some top margin
        inputPanel.setBackground(Color.WHITE);

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Add internal padding to the text field
        inputField.setBorder(BorderFactory.createCompoundBorder(
            inputField.getBorder(), 
            new EmptyBorder(5, 8, 5, 8))
        );
        inputPanel.add(inputField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBackground(new Color(33, 150, 243));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Add padding to the button to make it larger
        sendButton.setBorder(new EmptyBorder(8, 18, 8, 18)); 
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        connectToServer();

        inputField.addActionListener(e -> sendMessage());
        sendButton.addActionListener(e -> sendMessage());
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread readThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        final String finalMsg = msg;
                        // IMPORTANT: Update UI on the Event Dispatch Thread (EDT)
                        SwingUtilities.invokeLater(() -> {
                            chatArea.append("Server: " + finalMsg + "\n");
                        });
                    }
                } catch (IOException e) {
                    SwingUtilities.invokeLater(() -> {
                        chatArea.append("Disconnected from server.\n");
                    });
                }
            });
            readThread.setDaemon(true);
            readThread.start();

        } catch (Exception e) {
            // Also update UI on the EDT
            SwingUtilities.invokeLater(() -> {
                chatArea.append("Cannot connect to server.\n");
            });
        }
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();
        if (msg.isEmpty()) return;

        chatArea.append("You: " + msg + "\n");
        out.println(msg);
        inputField.setText("");

        if (msg.equalsIgnoreCase("bye")) {
            dispose();
        }
    }

    public static void main(String[] args) {
        try {
            // Set the native system Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Enable anti-aliased text
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            ChatClientGUI client = new ChatClientGUI();
            client.setVisible(true);
        });
    }
}
