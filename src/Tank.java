import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Tank {
    public int x,y,speed;
    public Image image;
    public int imageWid;

    public Tank(){

    }
    public Tank(int x,int y,int speed,String TANK_IMG,int imageWid){
        this.x=x;
        this.y=y;
        this.speed=speed;
        this.image=new Image(TANK_IMG);
        this.imageWid=imageWid;
    }


    public void draw(GraphicsContext gc){
        double imgWid=this.image.getWidth();
        double imgHei=this.image.getHeight();
        gc.drawImage(this.image,this.x,this.y,this.imageWid,this.imageWid*imgHei/imgWid);
    }

    public void move(KeyCode code, int sceneWid, int sceneHei){
        switch (code) {
            case UP:
                y -= speed;  // 上移
                break;
            case DOWN:
                y += speed;  // 下移
                break;
            case LEFT:
                x -= speed;  // 左移
                break;
            case RIGHT:
                x += speed;  // 右移
                break;
            default:
                break;

        }
        double imgWid=this.image.getWidth();
        double imgHei=this.image.getHeight();
        x=Math.max(0, Math.min(sceneWid-this.imageWid, x));
        y=Math.max(0, Math.min((int)(sceneHei-this.imageWid*imgHei/imgWid),y));
    }

}
