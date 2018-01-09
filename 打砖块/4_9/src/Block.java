import java.awt.*;
public class Block {

    private GamePanel gameP;    
    private Point allocation;
    public Point location[];
    public Point size;
    public int num;
    public boolean exist[];
    public int hp[];

    public Block(GamePanel gp) {
        gameP = gp;
        num = 20;
        allocation = new Point(4, 5);
        size = new Point(50, 20);
        location = new Point[num];
        for (int i = 0; i < allocation.y; i++) {
            for (int j = 0; j < allocation.x; j++) {
                location[i * allocation.x + j] = new Point((gp.width / allocation.x - size.x) / 2 + j * (gp.width / allocation.x), (i * 2 + 1) * size.y);
                System.out.println(location[i * allocation.x + j]);

            }
        }

        exist = new boolean[num];
        for (int i = 0; i < num; i++) {
            exist[i] = true;
            hp[i]=2;
        }
    }

    public void update() {
    }

    public void draw(Graphics g) {
        g.setColor(Color.gray);
        for (int i = 0; i < num; i++) {
            if (exist[i] == true) {
                g.fillRect(location[i].x, location[i].y, size.x, size.y);
            }
        }
    }
}
