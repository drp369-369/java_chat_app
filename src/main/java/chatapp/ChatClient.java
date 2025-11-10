package chatapp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 5000);
            System.out.println("✅ Connected to Chat Server!");

            // Input/output streams
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            // Show first server message
            System.out.println("Server: " + in.readLine());

            // Thread to read messages from server
            Thread readThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Server: " + msg);
                    }
                } catch (Exception e) {
                    System.out.println("❌ Disconnected from server.");
                }
            });
            readThread.setDaemon(true);
            readThread.start();

            // Send messages to server
            while (true) {
                String msg = scanner.nextLine();
                out.println(msg);

                if (msg.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            socket.close();
            scanner.close();

        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
