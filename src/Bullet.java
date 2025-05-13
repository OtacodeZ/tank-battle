import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
    private double x;
    private double y;
    private final double speed = 7;

    public Bullet(double startX, double startY) {
        this.x = startX;
        this.y = startY;
    }

    public void update() {
        y -= speed;  // 子弹向上飞行
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, 6, 10);  // 使用椭圆形代表子弹
    }

    public boolean isOffScreen() {
        return y < -10;
    }
}

