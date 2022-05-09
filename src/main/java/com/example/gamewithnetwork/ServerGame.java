package com.example.gamewithnetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGame {
    static int portnumber = 1234;
    static Socket clientSocket = null;

    public static void main(String[] args) {

        ServerSocket serverSocket = openToServer();
        System.out.println("Server started on port: " + serverSocket.getLocalPort());
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("accept fallita" + e);
            }

            System.out.println(clientSocket.getLocalAddress());

            ClientHandler clientHandler = new ClientHandler(clientSocket);

            new Thread(clientHandler).start();

            if (serverSocket.isClosed()) {
                System.out.println("client disconnected");
            }
        }
    }

    static private ServerSocket openToServer() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(portnumber);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return serverSocket;
    }
}
