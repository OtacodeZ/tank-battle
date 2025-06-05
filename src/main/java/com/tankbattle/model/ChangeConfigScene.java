package com.tankbattle.model;

import com.tankbattle.app.Main;
import com.tankbattle.config.GameConfig;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangeConfigScene {
    private Scene scene;
    private Stage stage;
    public ChangeConfigScene(Stage stage, Scene homeScene){
        this.stage=stage;
        Button btn1=new Button("返回");
        btn1.setOnAction(e -> {
            stage.setScene(homeScene);
        });

        //HP
        Text textHP=new Text("玩家初始HP:");
        Slider sliderHP = new Slider(0, 200, 100);
        sliderHP.setShowTickLabels(true);   // 显示数字标签（如50）
        sliderHP.setShowTickMarks(true);   // 显示刻度线
        sliderHP.setMajorTickUnit(10);     // 每10一格
        sliderHP.setMinorTickCount(4);     // 每个主刻度之间有4个小刻度
        sliderHP.setBlockIncrement(1);     // 拖动时步进为 1
        GameConfig.GAMER_HP_INIT.bindBidirectional(sliderHP.valueProperty());



        //Speed
        Text textSpeedA=new Text("玩家A速度：");
        Slider sliderSpeedA = new Slider(0, 20, 10);
        sliderSpeedA.setShowTickLabels(true);   // 显示数字标签（如50）
        sliderSpeedA.setShowTickMarks(true);   // 显示刻度线
        sliderSpeedA.setMajorTickUnit(1);     // 每10一格
        sliderSpeedA.setMinorTickCount(4);     // 每个主刻度之间有4个小刻度
        sliderSpeedA.setBlockIncrement(1);     // 拖动时步进为 1
        GameConfig.GAMER_SPEED.bindBidirectional(sliderSpeedA.valueProperty());



        VBox root=new VBox(btn1,textHP,sliderHP,textSpeedA,sliderSpeedA);
        scene = new Scene(root, Main.sceneWid, Main.sceneHei);
    }
    public Scene getScene() {
        return scene;
    }

    public void start() {

    }

    public void stop() {

    }
}
