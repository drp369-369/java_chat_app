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

        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        connect();

        inputField.addActionListener(e -> {
            String msg = inputField.getText();
            out.println(msg);
            inputField.setText("");
        });
    }

    private void connect() {
        try {
            Socket socket = new Socket("localhost", 5000);

            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read messages in a thread
            Thread t = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        chatArea.append(msg + "\n");
                    }
                } catch (Exception e) {
                    chatArea.append("Disconnected from server.\n");
                }
            });

            t.setDaemon(true);
            t.start();

        } catch (Exception e) {
            chatArea.append("Cannot connect to server.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClientGUI().setVisible(true));
    }
}

