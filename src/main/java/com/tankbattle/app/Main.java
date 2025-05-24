package com.tankbattle.app;
import com.tankbattle.model.Mode1;
import com.tankbattle.model.Mode2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {



     public static int sceneWid=800;
     public static int sceneHei=600;

    private Stage primaryStage;
    private Scene startScene;
    private Mode1 mode1;
    private Mode2 mode2;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // --- 开始界面 ---
        Button btn1 = new Button("进入模式1");
        Button btn2 = new Button("进入模式2");

        btn1.setOnAction(e -> {
            primaryStage.setScene(mode1.getScene());
            mode1.start();
        });

        btn2.setOnAction(e -> {
            primaryStage.setScene(mode2.getScene());
            mode2.start();
        });

        VBox layout = new VBox(10, new Text("选择模式"), btn1, btn2);
        startScene = new Scene(layout, 400, 300);

        // 初始化两个模式
        mode1 = new Mode1(primaryStage, startScene);
        mode2 = new Mode2(primaryStage, startScene);

        // 设置初始界面
        primaryStage.setScene(startScene);
        primaryStage.setTitle("坦克大战");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void sizeChangeable(Stage stage, Canvas canvas){
        //可全屏化
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            sceneWid=newVal.intValue();
            canvas.setWidth(newVal.doubleValue());

        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            sceneHei=newVal.intValue();
            canvas.setHeight(newVal.doubleValue());
        });
    }


}
