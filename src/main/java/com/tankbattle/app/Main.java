package com.tankbattle.app;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.tankbattle.ui.Background;
import com.tankbattle.model.Tank;
import resource.config.ImagePath;

import java.util.HashSet;
import java.util.Set;

public class Main extends Application {
    final private int sceneWid=800;
    final private int sceneHei=600;



    public static void main(String[] args) {


        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {


        //画布与场景
        Canvas canvas=new Canvas(sceneWid,sceneHei);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, sceneWid, sceneHei);

        // Background and Tank
        Background bg=new Background();

        Tank tank=new Tank(350,500,2, ImagePath.TANK_IMG,50);

        //interactKeyboard();
        Set<KeyCode> keysPressed = new HashSet<>();
        scene.setOnKeyPressed(event ->
                keysPressed.add(event.getCode()));
        scene.setOnKeyReleased(event ->
                keysPressed.remove(event.getCode()));

        //动画
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                tank.move(keysPressed,sceneWid,sceneHei);
                bg.draw(gc,sceneWid,sceneHei);
                tank.draw(gc, tank.x, tank.y);

            }
        };
        timer.start();


        primaryStage.setTitle("坦克大战");
        primaryStage.setScene(scene);
        primaryStage.show();



    }


}