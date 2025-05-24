package com.tankbattle.model;

import com.tankbattle.app.Main;
import com.tankbattle.ui.Background;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import resource.config.ImageManger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//
public class Mode1 {
    private Scene scene;
    private AnimationTimer gameLoop;

    private String ifGenerateTankB="yes";
    public Mode1(Stage stage, Scene startScene) {





        //classes
        TankGamerA tankGamerA=new TankGamerA(150,150,50, ImageManger.tankGamerA,5);

        TankGamerB tankGamerB =new TankGamerB(150,200,50,ImageManger.tankGamerA,5);

        EnemyManager enemyManager=new EnemyManager();

        Background bg=new Background();
        Canvas canvas=new Canvas(Main.sceneWid,Main.sceneHei);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        Group root = new Group(canvas);
//

        scene = new Scene(root, Main.sceneWid, Main.sceneHei);

        //interactKeyboard();
        Set<KeyCode> keysPressed = new HashSet<>();
        scene.setOnKeyPressed(event ->
                keysPressed.add(event.getCode()));
        scene.setOnKeyReleased(event ->
                keysPressed.remove(event.getCode()));
        // 游戏主循环
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
                draw();

            }
            private void update(long now){
                tankGamerA.update(now,keysPressed,Main.sceneWid,Main.sceneHei);
                tankGamerB.update(now,keysPressed,Main.sceneWid,Main.sceneHei);

                CollisionManager.update();

                enemyManager.update(now,tankGamerA,tankGamerB,Main.sceneWid,Main.sceneHei);
            }

            private void draw(){
                bg.draw(gc,Main.sceneWid,Main.sceneHei);
                tankGamerA.draw(gc);
                tankGamerB.draw(gc);
                enemyManager.draw(gc);

            }
        };
    }

    public Scene getScene() {
        return scene;
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }
}

