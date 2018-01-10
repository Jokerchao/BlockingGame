import java.awt.*;
import java.util.Random;

public class Bonus {


    public Point location;
    public Point size;
    public boolean exist;
    public int hp;
    public int type;
    private Block blk;
    private GamePanel gameP;


    public Bonus(GamePanel gp,Block blk) {
        gameP = gp;
        size = new Point(50, 20);
        Random random = new Random();
        int randomBlk=random.nextInt(blk.num);
        System.out.print(randomBlk);
        location = blk.location[randomBlk];
        blk.exist[randomBlk]=false;
        type=random.nextInt(3);
        exist=true;
        hp=2;
    }

    public void update() {
    }

    public void draw(Graphics g) {
        g.setColor(Color.gray);
        if(exist){
            g.fillRect(location.x, location.y, size.x, size.y);
        }
        if(hp==1){
            g.setColor(Color.green);
            g.fillRect(location.x, location.y, size.x, size.y);
        }


    }

}
