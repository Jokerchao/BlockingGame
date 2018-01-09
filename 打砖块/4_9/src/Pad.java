import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Pad {

    public Point location;
    public Point size;
    private GamePanel gameP;

    public Pad(GamePanel gp) {
        gameP = gp;
        //x为宽度，y为高度
        size = new Point(100, 20);
        //位置固定为窗口下方的正中
        location = new Point((gp.width - size.x) / 2, gp.heigth - size.y);

    }

    public void update() {
        if (gameP.padMoveRight) {
            if (location.x + size.x < gameP.width) {
                location.x += 10;
            }
        }
        if (gameP.padMoveLeft) {
            if (location.x > 0) {
                location.x -= 10;
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRoundRect(location.x, location.y, size.x, size.y,10,10);
    }
}
