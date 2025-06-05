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
        Text textSpeedGamer =new Text("玩家速度：");
        Slider sliderSpeedGamer = new Slider(0, 20, 10);
        sliderSpeedGamer.setShowTickLabels(true);   // 显示数字标签（如50）
        sliderSpeedGamer.setShowTickMarks(true);   // 显示刻度线
        sliderSpeedGamer.setMajorTickUnit(1);     // 每10一格
        sliderSpeedGamer.setMinorTickCount(4);     // 每个主刻度之间有4个小刻度
        sliderSpeedGamer.setBlockIncrement(1);     // 拖动时步进为 1
        GameConfig.GAMER_SPEED.bindBidirectional(sliderSpeedGamer.valueProperty());

        Text textSpeedE=new Text("玩家速度：");
        Slider sliderSpeedE = new Slider(0, 20, 1);
        sliderSpeedE.setShowTickLabels(true);   // 显示数字标签（如50）
        sliderSpeedE.setShowTickMarks(true);   // 显示刻度线
        sliderSpeedE.setMajorTickUnit(1);     // 每10一格
        sliderSpeedE.setMinorTickCount(4);     // 每个主刻度之间有4个小刻度
        sliderSpeedE.setBlockIncrement(1);     // 拖动时步进为 1
        GameConfig.ENEMY_SPEED.bindBidirectional(sliderSpeedE.valueProperty());



        VBox root=new VBox(btn1,textHP,sliderHP, textSpeedGamer, sliderSpeedGamer,textSpeedE,sliderSpeedE);
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
