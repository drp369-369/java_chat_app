package chatapp;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("✅ Server started... waiting for client");

            Socket socket = server.accept();
            System.out.println("✅ Client connected");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // SEND WELCOME MESSAGE
            out.println("Welcome to the chat!");

            // MAIN LOOP
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("Client: " + msg);
                if (msg.equalsIgnoreCase("bye")) break;

                out.println("Server received: " + msg);
            }

            socket.close();
            server.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }
}
