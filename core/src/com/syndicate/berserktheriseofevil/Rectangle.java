package com.syndicate.berserktheriseofevil;

public class Rectangle {

    public float x = 0f;
    public float y = 0f;
    public float width = 0f;
    public float height = 0f;

    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Rectangle() {

    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean contains(float x, float y) {
        return x >= this.x && y >= this.y && x + width <= this.x + width && y + height <= this.y + height;
    }


}
