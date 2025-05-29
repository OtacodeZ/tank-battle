package com.tankbattle.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import com.tankbattle.config.ImageManger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public  class EnemyManager {
    final public static List<Enemy> enemies=new ArrayList<>();

    public void update(long now,Tank tank1, Tank tank2,int sceneWid, int sceneHei){
        //spawn move and remove
        decideAndSpawn(enemies,now);
        Iterator<Enemy> iterator=enemies.iterator();
        while (iterator.hasNext()){
            Enemy enemy=iterator.next();
            if(enemy.ifDie()){
                iterator.remove();
                CollisionManager.collidables.remove(enemy);
            }
            enemy.update(now,tank1,tank2,sceneWid,sceneHei);
        }
    }
    public void draw(GraphicsContext gc, Stage stage){
        for(Enemy enemy:enemies){
            enemy.draw(gc,stage);
        }
    }


    private static long lastEnemySpawnTime=0;
    final private static long enemySpawnInterval=1_000_000_000;
    private int enemyHave=enemies.size();
    private void decideAndSpawn(List<Enemy> enemies, long now){
        enemyHave=enemies.size();
        System.out.println("EH:"+enemyHave);
        if(enemyHave<1){
            if (now - lastEnemySpawnTime > enemySpawnInterval) {
                int x = (int)(Math.random() * (800 - 40));// 屏幕宽度减去敌人宽度
                int y = (int)(Math.random() * (600 - 40));//HERE


                enemies.add(new Enemy(x, y,50, ImageManger.enemy,1));
//                protected Enemy(int x, int y, double width, Image image, int speed)
                lastEnemySpawnTime = now;
            }
        }

    }
}
