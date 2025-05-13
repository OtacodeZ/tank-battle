//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private int sceneWid=800;
    private int sceneHei=600;



    public static void main(String[] args) {


        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {



        Canvas canvas=new Canvas(sceneWid,sceneHei);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, sceneWid, sceneHei);

        //
        Background bg=new Background();
        bg.draw(gc,sceneWid,sceneHei);

        Tank tank=new Tank(350,500,5,ImagePath.TANK_IMG,50);
        tank.draw(gc);

        //interactKeyboard();
        scene.setOnKeyPressed(event -> {

           tank.move(event.getCode(),sceneWid,sceneHei);

            bg.draw(gc,sceneWid,sceneHei);
            tank.draw(gc);

        });






        primaryStage.setTitle("坦克大战");
        primaryStage.setScene(scene);
        primaryStage.show();



    }


}