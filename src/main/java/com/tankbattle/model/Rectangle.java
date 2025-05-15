package com.tankbattle.model;

public class Rectangle {
    int aX,aY,aW,aH;
    int bX,bY,bW,bH;
    public boolean intersects(){
        if (aX + aW > bX &&
                aX < bX + bW &&
                aY + aH > bY &&
                aY < bY + bH) {
            return true;
        }else {
            return false;
        }
    }
}
