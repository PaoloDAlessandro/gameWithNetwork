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
    int life = 5;
    Label life_label = new Label("Life: " + life);
    PrintWriter out = null;
    BufferedReader in = null;
    Socket echoSocket = null;
    BufferedReader stdIn;
    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    Timer timer = new Timer();
    int screenWidth = 0;
    int step = 0;
    int score = 0;

    public static void main(String[] args) throws IOException {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Sprite sp = new Sprite("mars.png", 500, 40);
        sprites.add(sp);
        stage.setTitle("Game");
        stage.setMaximized(true);
        life_label.setFont(new Font(18));
        life_label.setTranslateX(10);
        life_label.setTranslateY(10);
        root.getChildren().add(life_label);
        stage.setScene(new Scene(root));
        stage.show();
        screenWidth = (int) stage.getWidth();
        root.getChildren().add(sprites.get(0).getImgView());
        initializeConnection();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    move();
                    step++;
                    if (step == 10) {
                        try {
                            executeConnection("{\"x\":0,\"y\":0}");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        step = 0;
                        System.out.println(step);
                    }
                });
            }
        };

        timer.schedule(task, 100, 200);

        root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int spIndex = 0;
                root.getChildren().remove(mouseEvent.getTarget());
                sprites.remove(mouseEvent.getTarget());
                for (Sprite sp : sprites) {
                    if ((sp.getImgView().getY() > mouseEvent.getY() - 50 && sp.getImgView().getY() < mouseEvent.getY() + 50) && (sp.getImgView().getX() > mouseEvent.getX() - 50 && sp.getImgView().getX() < mouseEvent.getX() + 50)) {
                        spIndex = sprites.indexOf(sp);
                    }
                }
                System.out.println(sprites);
                sprites.remove(spIndex);
                score++;
                System.out.println(sprites.toString());

                try {
                    Point p = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
                    Gson gson = new Gson();
                    String json = gson.toJson(p);
                    System.out.println(json);
                    executeConnection(json);
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

        out.println(screenWidth);
    }

    public void executeConnection(String point) throws IOException {
        Gson gson = new Gson();
        out.println(point);
        System.out.println("sended: " + point);
        if(score % 2 == 0) {
            String received = "";
            try {
                received = in.readLine();
                System.out.println("received: " + received);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            SpriteInfo spi = gson.fromJson(received, SpriteInfo.class);
            Sprite sp = new Sprite(spi.getImgName(), spi.getX(), spi.getY());
            sprites.add(sp);
            root.getChildren().add(sp.getImgView());
            System.out.println(sprites);
        }

    }

    public void move() {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).moveBottom(20);
            if (sprites.get(i).getImgView().getY() > 680) {
                root.getChildren().remove(sprites.get(i).getImgView());
                sprites.remove(i);
                life--;
                life_label.setText("Life: " + life);

                if (life <= 0) {
                    timer.cancel();
                    life_label.setText("GAME OVER");
                }
            }
        }
    }
}

