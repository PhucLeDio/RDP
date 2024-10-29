package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private PrintWriter writer;
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;

    public ChatClient(Socket socket) {
        this.socket = socket;
        setupUI();
        startListening();
    }

    private void setupUI() {
        frame = new JFrame("Chat");
        textArea = new JTextArea(10, 30);
        textField = new JTextField(30);
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(textField.getText());
                textField.setText("");
            }
        });

        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(sendButton, BorderLayout.EAST);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void sendMessage(String message) {
        try {
            if (writer == null) {
                writer = new PrintWriter(socket.getOutputStream(), true);
            }
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startListening() {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while ((message = reader.readLine()) != null) {
                    updateChatArea(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateChatArea(String message) {
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
    }
}
