package com.tankbattle.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollisionManager {
    public static List<Collidable> collidables = new ArrayList<>();


    public static void update() {
        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {
                Collidable a = collidables.get(i);
                Collidable b = collidables.get(j);
                if (a.getBounds().intersects(b.getBounds())) {
                    a.onCollide(b);
                    b.onCollide(a);
                }
            }
        }
    }

}
