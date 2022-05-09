package com.example.gamewithnetwork;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite implements Movable{
    String imgName;
    int x;
    int y;
    ImageView imgView;

    Sprite(String imgName, int x, int y) {
        this.imgName = imgName;
        this.x = x;
        this.y = y;
        Image img = new Image("mars.png");
        this.imgView = new ImageView(img);
        imgView.setFitWidth(100);
        imgView.setFitHeight(100);
        imgView.setX(x);

    }

    public void moveRight(int value) {
        this.x += value;
        updatePos();
    }

    public void moveLeft(int value) {
        this.x -= value;
        updatePos();
    }

    public void moveBottom(int value) {
        this.y += value;
        updatePos();
    }

    @Override
    public void moveTop(int value) {
        this.y -= value;
    }

    public void updatePos() {
        imgView.setX(this.x);
        imgView.setY(this.y);
    }


    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

}
