package com.example.gamewithnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import com.google.gson.Gson;


public class ClientHandler implements Runnable{
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
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
        SpriteInfo si = new SpriteInfo("earth.png", 200, 200);
        Gson gson = new Gson();
        String json = gson.toJson(si);
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
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

    static private String editString(int start, int end, String s) {
        try {
            return s.substring(start, end);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("ERRORE");
        }
        return null;
    }

    static private int searchBlank(String s) {
        if (s.indexOf("+") != -1) {
            return s.indexOf("+");
        }
        if (s.indexOf("-") != -1) {
            return s.indexOf("-");
        }
        if (s.indexOf("/") != -1) {
            return s.indexOf("/");
        }
        if (s.indexOf("*") != -1) {
            return s.indexOf("*");
        }
        return -1;
    }
}

