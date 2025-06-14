package com.tankbattle.model;

import com.tankbattle.app.Main;
import com.tankbattle.config.GameConfig;
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
    final private static long enemySpawnInterval=GameConfig.ENEMY_SPAWN_INTERVAL;
    private int enemyHave=enemies.size();
    private void decideAndSpawn(List<Enemy> enemies, long now){
        enemyHave=enemies.size();
        System.out.println("EH:"+enemyHave);
        if(enemyHave< GameConfig.ENEMY_MOST){
            if (now - lastEnemySpawnTime > enemySpawnInterval) {
                int x =0;
                int y =0;
                if (GameConfig.ENEMY_SPAWN_KIND.equalsIgnoreCase("random")){
                    x=(int)(Math.random() * (Main.sceneWid - GameConfig.ENEMY_WIDTH));
                    y = (int)(Math.random() * (Main.sceneHei - GameConfig.ENEMY_WIDTH));//HERE
                }
                else {
                    //HERE
                }


                enemies.add(new Enemy(x, y,GameConfig.ENEMY_WIDTH, ImageManger.enemy,GameConfig.ENEMY_SPEED.get()));
                lastEnemySpawnTime = now;
            }
        }

    }
}
