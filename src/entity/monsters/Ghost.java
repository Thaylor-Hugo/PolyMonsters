package entity.monsters;

import javax.swing.ImageIcon;

import main.GamePanel;

public class Ghost extends Monsters {
    final int tilesToMove = 5;
    int totalMoved = 0;
    boolean movingX = true;     // false means movingY
    boolean forward = true;     // false means backwards
    private static String battlePath = "resources/monsters/ghosts/ghost1.gif";

    public Ghost(GamePanel gp, int mapX, int mapY) {
        this.gp = gp;
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 3;
        mapX = gp.tileSize;
        mapY = gp.tileSize;
        speed = 2;
        moving = true;
        battleImage = new ImageIcon(battlePath).getImage();
    }

    @Override
    public void update() {
        // TODO better moviment strategy
        int totalToMove = tilesToMove * gp.tileSize;
        if (movingX) {
            if (forward) mapX += speed;
            else mapX -= speed;
            totalMoved += speed;
            
        } else {
            if (forward) mapY += speed;
            else mapY -= speed;
            totalMoved += speed;
        }

        if (totalMoved >= totalToMove) {
            if (movingX && forward) {
                movingX = false;
            }else if (!movingX && forward) {
                movingX = true;
                forward = false;
            }else if (movingX && !forward) {
                movingX = false;
            }else if (!movingX && !forward) {
                movingX = true;
                forward = true;
            }
            totalMoved = 0;
        }
    }

    @Override
    protected String getEntityImagePath() {
        return battlePath;
    }
}
