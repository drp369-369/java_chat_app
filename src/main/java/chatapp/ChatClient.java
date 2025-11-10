package chatapp;

import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server running...");
            try (Socket client = serverSocket.accept()) {
                System.out.println("Client connected.");

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                // Send welcome message
                out.println("Welcome to the Chat Server!");
                out.println("You can type 'bye' anytime to exit the chat.");

                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Received: " + msg);

                    String reply;
                    String m = msg.trim().toLowerCase();

                    // Basic chatbot responses
                    if (m.equals("hi") || m.equals("hello")) {
                        reply = "Hello there! How can I help you today?";
                    } else if (m.contains("how are you")) {
                        reply = "I'm just a simple chatbot, but I'm doing great!";
                    } else if (m.contains("what is your name")) {
                        reply = "I'm ChatBot v1.0, created using Java sockets.";
                    } else if (m.contains("what can you do")) {
                        reply = "I can chat with you, answer simple questions, and keep you company!";
                    } else if (m.contains("who created you")) {
                        reply = "I was created by a team of computer science students for a java project.";
                    } else if (m.contains("where are you from")) {
                        reply = "I live inside your computer, running on localhost port 5000.";
                    } else if (m.contains("what is java")) {
                        reply = "Java is a programming language used to build applications like me.";
                    } else if (m.contains("tell me a fact")) {
                        reply = "Fun fact: The first version of Java was released in 1995.";
                    } else if (m.contains("bye")) {
                        reply = "Goodbye! It was nice chatting with you.";
                        out.println(reply);
                        break;
                    } else {
                        reply = "I'm not sure how to respond to that, but it sounds interesting!";
                    }

                    out.println(reply);
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
