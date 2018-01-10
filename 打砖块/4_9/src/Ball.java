import java.awt.*;

public class Ball {

    public int hp;
    public int score;
    public static final int SPEEDDOWN = 0;
    public static final int WIDTHINCREATE =1;
    public static final int INVICIBLE = 2;
    public static final int STOP = 3;
    private Point location;
    private int diameter;
    private int dx;
    private int dy;
    private GamePanel gameP;
    private Block blk;
    private Pad pd;
    private Bonus bonus;
    private boolean padCanBounce = false;
    private boolean blockCanBounce = false;


    public Ball(GamePanel gp, Pad p, Block bk,Bonus bns) {
        gameP = gp;
        blk = bk;
        pd = p;
        bonus=bns;
        diameter = 20;
        score=0;
        hp=1000;
        blockCanBounce=true;
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
            bonusBounced();
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
                if(blockCanBounce){
                    dy = -dy;
                }
                padCanBounce = true;
                blk.hp[i]--;
                score++;
                if(blk.hp[i]==0){
                    blk.exist[i] = false;
                }

            } else if (Bounce(local1, size1)) {
                if(blockCanBounce){
                    if (dx > 0) {
                        dx = -dx;
                    } else {
                        dy = -dy;
                    }
                }
                blk.hp[i]--;
                score++;
                if(blk.hp[i]==0){
                    blk.exist[i] = false;
                }
                padCanBounce = true;
            } else if (Bounce(local3, size3)) {
                if(blockCanBounce){
                    if (dx < 0) {
                        dx = -dx;
                    } else {
                        dy = -dy;
                    }
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

    public void bonusBounced() {
        Point local1, local2, local3, size1, size2, size3;
        if(bonus.exist){
            local1 = bonus.location;
            size1 = new Point(bonus.size.x * 1 / 10, bonus.size.y);
            local2 = new Point(bonus.location.x + bonus.size.x * 1 / 10, bonus.location.y);
            size2 = new Point(bonus.size.x * 8 / 10, bonus.size.y);
            local3 = new Point(bonus.location.x + bonus.size.x * 9 / 10, bonus.location.y);
            size3 = new Point(bonus.size.x * 1 / 10, bonus.size.y);
            if (Bounce(local2, size2)) {
                dy = -dy;
                padCanBounce = true;
                bonus.hp--;
                score++;
                if(bonus.hp==0){
                    bonus.exist = false;
                    setBonus();
                }

            } else if (Bounce(local1, size1)) {
                if (dx > 0) {
                    dx = -dx;
                } else {
                    dy = -dy;
                }
                bonus.hp--;
                score++;
                padCanBounce = true;
                if(bonus.hp==0){
                    bonus.exist = false;
                    setBonus();
                }

            } else if (Bounce(local3, size3)) {
                if (dx < 0) {
                    dx = -dx;
                } else {
                    dy = -dy;
                }
                bonus.hp--;
                score++;
                padCanBounce = true;
                if(bonus.hp==0){
                    bonus.exist = false;
                    setBonus();
                }

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

    public void setBonus(){
        switch (bonus.type){
            case SPEEDDOWN:
                if(dx>0){
                    dx-=3;
                }else{
                    dx+=3;
                }
                if(dy>0){
                    dy-=3;
                }else{
                    dy+=3;
                }
                break;
            case WIDTHINCREATE:
                pd.size.x+=20;
                break;
            case INVICIBLE:
                blockCanBounce=false;
                break;
            case STOP:
                padCanBounce=false;
                break;

        }
    }
}
