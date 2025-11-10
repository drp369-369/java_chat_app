package chatapp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to Chat Server!");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // Read welcome message
            System.out.println(input.readLine());

            // Sending messages
            while (true) {
                System.out.print("You: ");
                String msg = scanner.nextLine();
                output.println(msg);

                if (msg.equalsIgnoreCase("bye")) {
                    break;
                }

                // Read server reply
                String reply = input.readLine();
                if (reply != null) {
                    System.out.println("Server: " + reply);
                }
            }

            socket.close();
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

