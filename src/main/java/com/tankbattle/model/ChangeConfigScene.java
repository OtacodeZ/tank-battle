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

        Text textHP=new Text("玩家初始HP:");
        Slider slider = new Slider(0, 200, 100);
        slider.setShowTickLabels(true);   // 显示数字标签（如50）
        slider.setShowTickMarks(true);   // 显示刻度线
        slider.setMajorTickUnit(10);     // 每10一格
        slider.setMinorTickCount(4);     // 每个主刻度之间有4个小刻度
        slider.setBlockIncrement(1);     // 拖动时步进为 1

        GameConfig.GAMER_HP_INIT.bindBidirectional(slider.valueProperty());
        Button btn1=new Button("返回");
        btn1.setOnAction(e -> {
            stage.setScene(homeScene);
        });



        VBox root=new VBox(btn1,textHP,slider);
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
