package com.tankbattle.model;

public interface Collidable {
    Rectangle getBounds(); // 获取碰撞盒
    void onCollide(Collidable other); // 被撞击时的响应
    CollisionType getType();
    Collidable getOwner();//没有owner的返回null
}
