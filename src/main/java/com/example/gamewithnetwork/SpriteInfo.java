package com.example.gamewithnetwork;

public class SpriteInfo {
     String imgName;
     int x;
     int y;

    public SpriteInfo(String imgName, int x, int y) {
        this.imgName = imgName;
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "SpriteInfo{" +
                "imgName='" + imgName + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
