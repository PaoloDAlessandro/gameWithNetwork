package com.example.gamewithnetwork;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGame extends Application {

    static String hostName = "127.0.0.1";
    static int portNumber = 1234;
    Group root = new Group();
    Label life_label = new Label("Life: " + 3);
    String userInput;
    PrintWriter out = null;
    BufferedReader in = null;
    Socket echoSocket = null;
    BufferedReader stdIn;
    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    Timer timer = new Timer();


    public static void main(String[] args) throws IOException {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Sprite sp = new Sprite("mars.png", 200, 200);
        sprites.add(sp);
        stage.setTitle("Game");
        stage.setMaximized(true);
        life_label.setFont(new Font(18));
        life_label.setTranslateX(10);
        life_label.setTranslateY(10);
        root.getChildren().add(life_label);
        stage.setScene(new Scene(root));
        stage.show();
        root.getChildren().add(sprites.get(0).getImgView());
        initializeConnection();
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int spIndex = 0;
                root.getChildren().remove(mouseEvent.getTarget());
                sprites.remove(mouseEvent.getTarget());
                for(Sprite sp : sprites) {
                    if ((sp.getImgView().getY() > mouseEvent.getY() - 50 && sp.getImgView().getY() < mouseEvent.getY() + 50) && (sp.getImgView().getX() > mouseEvent.getX() - 50 && sp.getImgView().getX() < mouseEvent.getX() + 50)) {
                        spIndex = sprites.indexOf(sp);
                    }
                }
                sprites.remove(spIndex);
                try {
                    String str = "{\"x\":" + "\"" + (int) mouseEvent.getX() + "\"," + "\"y\":" + "\"" + (int) mouseEvent.getY() + "\"}";
                    Gson gson = new Gson();
                    String send = gson.toJson(str);
                    System.out.println(send);
                    executeConnection(send);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initializeConnection() throws IOException {
        try {
            echoSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("cannot reach server " + e);
        }


        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));

        in = new BufferedReader(
                new InputStreamReader(echoSocket.getInputStream()));

    }

    public void executeConnection(String send) throws IOException {
                out.println(send);
                System.out.println("sended: " + userInput);
                try {
                    System.out.println("received: " + in.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
}
