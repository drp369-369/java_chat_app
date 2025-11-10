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

                // Send welcome message
                out.println("Welcome!");

                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Received: " + msg);
                    String reply;
                    String m = msg.trim().toLowerCase();

                    // Basic chatbot replies
                    if (m.equals("hi") || m.equals("hello")) {
                        reply = "Hello there! How can I help you today?";
                    } else if (m.contains("how are you")) {
                        reply = "I'm doing great! Thanks for asking.";
                    } else if (m.contains("what is your name")) {
                        reply = "I'm your friendly Java ChatBot.";
                    } else if (m.contains("what can you do")) {
                        reply = "I can chat, answer questions, and make your project look cool!";
                    } else if (m.contains("who created you")) {
                        reply = "I was created by a Computer Science student for a Java networking project.";
                    } else if (m.contains("where are you from")) {
                        reply = "I live inside your computer on localhost:5000.";
                    } else if (m.contains("tell me a fact")) {
                        reply = "Fun fact: The first version of Java was released in 1995!";
                    } else if (m.contains("bye")) {
                        reply = "Goodbye! See you soon.";
                        out.println("Server: " + reply);
                        break;
                    } else {
                        reply = "I'm not sure how to respond to that, but it sounds interesting!";
                    }

                    out.println("Server: " + reply);
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

