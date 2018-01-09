import java.awt.*;

public class Ball {

    public int hp;
    public int score;
    private Point location;
    private int diameter;
    private int dx;
    private int dy;
    private GamePanel gameP;
    private Block blk;
    private Pad pd;
    private boolean padCanBounce = false;


    public Ball(GamePanel gp, Pad p, Block bk) {
        gameP = gp;
        blk = bk;
        pd = p;
        diameter = 20;
        score=0;
        hp=3;
        location = new Point(pd.location.x + (pd.size.x - diameter) / 2, pd.location.y - pd.size.y);

        dx = 5;
        dy = -5;
    }

    public void update() {

        if (gameP.ballMove) {
            location.x += dx;
            location.y += dy;
            wallBounced();
            blockBounced();
            padBounced();
        } else {
            location.setLocation(pd.location.x + (pd.size.x - diameter) / 2, pd.location.y - pd.size.y);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.setFont(new Font("宋体", Font.BOLD, 45));
        g.fillOval(location.x, location.y, diameter, diameter);
        String result="你的得分为："+score;
        if(gameP.gameOver==true){
            g.clearRect(0,0,gameP.width,gameP.heigth);
            g.drawString(result,50,50);
        }
    }

    public boolean Bounce(Point bk_location, Point bk_size) {
        if ((location.x > bk_location.x - diameter) && (location.x < bk_location.x + bk_size.x) && (location.y > bk_location.y - diameter) && (location.y < bk_location.y + bk_size.y)) {
            return true;
        } else {
            return false;
        }

    }

    public void wallBounced() {
        if ((location.x > gameP.width - diameter) || (location.x < 0)) {
            dx = -dx;
            padCanBounce = true;
        }

        if (location.y < 0) {
            dy = -dy;
            padCanBounce = true;
        }
        if (location.y > gameP.heigth) {
            hp--;
            gameP.ballMove = false;
            padCanBounce = true;
        }

    }

    public void blockBounced() {
        Point local1, local2, local3, size1, size2, size3;
        for (int i = 0; i < blk.num; i++) {
        	
        	if(blk.exist[i]==false) continue;
        	
            local1 = blk.location[i];
            size1 = new Point(blk.size.x * 1 / 10, blk.size.y);
            local2 = new Point(blk.location[i].x + blk.size.x * 1 / 10, blk.location[i].y);
            size2 = new Point(blk.size.x * 8 / 10, blk.size.y);
            local3 = new Point(blk.location[i].x + blk.size.x * 9 / 10, blk.location[i].y);
            size3 = new Point(blk.size.x * 1 / 10, blk.size.y);


            if (Bounce(local2, size2)) {
                dy = -dy;
                padCanBounce = true;
                blk.hp[i]--;
                score++;
                if(blk.hp[i]==0){
                    blk.exist[i] = false;
                }

            } else if (Bounce(local1, size1)) {
                if (dx > 0) {
                    dx = -dx;
                } else {
                    dy = -dy;
                }
                blk.hp[i]--;
                score++;
                if(blk.hp[i]==0){
                    blk.exist[i] = false;
                }
                padCanBounce = true;
            } else if (Bounce(local3, size3)) {
                if (dx < 0) {
                    dx = -dx;
                } else {
                    dy = -dy;
                }
                blk.hp[i]--;
                score++;
                if(blk.hp[i]==0){
                    blk.exist[i] = false;
                }
                padCanBounce = true;
            }
        }

    }

    public void padBounced() {
        Point local1, local2, local3, size1, size2, size3;
        local1 = pd.location;
        size1 = new Point(pd.size.x * 1 / 7, pd.size.y / 4);
        local3 = new Point(pd.location.x + pd.size.x * 6 / 7, pd.location.y);
        size3 = new Point(pd.size.x * 1 / 7, pd.size.y / 4);
        local2 = new Point(pd.location.x + pd.size.x * 1 / 7, pd.location.y);
        size2 = new Point(pd.size.x * 5 / 7, pd.size.y / 4);

        if (padCanBounce) {
            if (Bounce(local2, size2)) {
                dy = -dy;
                padCanBounce = false;
            } else if (Bounce(local1, size1)) {
                padCanBounce = false;
                if (dx > 0) {
                    dx = -dx;
                }
                dy = -dy;
            } else if (Bounce(local3, size3)) {
                padCanBounce = false;
                if (dx < 0) {
                    dx = -dx;
                }
                dy = -dy;
            }
        }
    }
}
