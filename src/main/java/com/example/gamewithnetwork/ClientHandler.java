package com.example.gamewithnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import com.google.gson.Gson;


public class ClientHandler implements Runnable{
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    int screenWidth = 0;
    String [] imgArray = {"earth.png", "mars.png", "pluto.png"};
    ArrayList<SpriteInfo> sprites = new ArrayList<SpriteInfo>();

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        this.initializeClientHandler();
        this.executeClientHandler();
    }

    private void initializeClientHandler() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("reader fallita" + e);
        }

        out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     void executeClientHandler() {
        try {
            screenWidth = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = "";

        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
                int randomX = (int) (Math.random() * screenWidth - 40) + 40;
                SpriteInfo si = new SpriteInfo(imgArray[(int) (Math.random() * 2)], randomX, 40);
                sprites.add(si);
                Gson gson = new Gson();
                String json = gson.toJson(si);
                out.println(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

