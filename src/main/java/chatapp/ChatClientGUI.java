package chatapp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI extends JFrame {
    private JTextArea chatArea = new JTextArea();
    private JTextField inputField = new JTextField();
    private PrintWriter out;

    public ChatClientGUI() {
        setTitle("Chat Client (GUI)");
        setSize(400, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        chatArea.append(" Connected to Chat Server! \nType your message below:\n\n");


        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        connectToServer();

        // Send message when pressing Enter
        inputField.addActionListener(e -> {
            String msg = inputField.getText();
            out.println(msg);
            inputField.setText("");

            if (msg.equalsIgnoreCase("bye")) {
                dispose();
            }
        });
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Thread to read messages from server
            Thread readThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        chatArea.append(msg + "\n");
                    }
                } catch (IOException e) {
                    chatArea.append("Disconnected from server.\n");
                }
            });

            readThread.setDaemon(true);
            readThread.start();

        } catch (Exception e) {
            chatArea.append("Cannot connect to server.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClientGUI().setVisible(true));
    }
}

