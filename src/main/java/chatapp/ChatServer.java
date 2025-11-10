package chatapp;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server running...");
            try (Socket client = serverSocket.accept()) {
                System.out.println("Client connected.");

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                out.println("Welcome to the Java Chat Server!");

                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Received: " + msg);
                    String lower = msg.trim().toLowerCase();
                    String reply;

                    if (lower.equals("hi") || lower.equals("hello"))
                        reply = "Hello! How can I help you today?";
                    else if (lower.contains("how are you"))
                        reply = "I'm doing well, thank you for asking!";
                    else if (lower.contains("what is your name"))
                        reply = "I'm ChatBot, your Java assistant.";
                    else if (lower.contains("who created you"))
                        reply = "I was created as part of a Computer Science networking project.";
                    else if (lower.contains("what can you do"))
                        reply = "I can chat with you and make your project presentation more fun!";
                    else if (lower.contains("tell me a fact"))
                        reply = "Did you know? The first Java version was released in 1995.";
                    else if (lower.contains("java"))
                        reply = "Java is an object-oriented language widely used in backend and Android development.";
                    else if (lower.contains("bye")) {
                        reply = "Goodbye! It was nice chatting with you.";
                        out.println(reply);
                        break;
                    } else
                        reply = "I'm not sure how to respond to that, but it's interesting!";

                    out.println(reply);
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
