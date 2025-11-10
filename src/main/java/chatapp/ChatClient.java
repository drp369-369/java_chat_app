package chatapp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("✅ Connected to Chat Server!");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // READ WELCOME MESSAGE
            String welcome = in.readLine();
            System.out.println("Server: " + welcome);

            // SEND + RECEIVE REPLY LOOP
            while (true) {
                System.out.print("You: ");
                String msg = scanner.nextLine();
                out.println(msg);

                if (msg.equalsIgnoreCase("bye")) break;

                String reply = in.readLine();
                if (reply != null) {
                    System.out.println("Server: " + reply);
                }
            }

            socket.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }
}

