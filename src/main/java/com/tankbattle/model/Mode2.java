package com.tankbattle.model;

import com.tankbattle.app.Main;
import com.tankbattle.config.GameConfig;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mode2 {
    private Scene scene;
    private Stage stage;
    public Mode2(Stage stage,Scene homeScene){
        this.stage=stage;

        Slider slider = new Slider(1, 100, 50);
        slider.setShowTickLabels(true);   // 显示数字标签（如50）
        slider.setShowTickMarks(true);   // 显示刻度线
        slider.setMajorTickUnit(10);     // 每10一格
        slider.setMinorTickCount(4);     // 每个主刻度之间有4个小刻度
        slider.setBlockIncrement(1);     // 拖动时步进为 1
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            GameConfig.GAMER_HP_INIT.set(newValue.intValue());
            // 这里可以设置坦克速度，比如 setTankSpeed(speed);
            System.out.println("NEW_HP:"+ GameConfig.GAMER_HP_INIT.get());
        });
        Button btn1=new Button("返回");
        btn1.setOnAction(e -> {
            stage.setScene(homeScene);
        });



        VBox root=new VBox(btn1,slider);
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
