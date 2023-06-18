package entity.monsters;

import entity.Player;
import main.GamePanel;

public class Ghost extends Monsters {
    final int tilesToMove = 5;
    int totalMoved = 0;
    boolean movingX = true;     // false means movingY
    boolean forward = true;     // false means backwards

    public Ghost(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        setDefaltValues();
    }

    @Override
    protected void setDefaltValues() {
        // TODO need to change defaut position
        visionRange = gp.tileSize * 3;
        mapX = player.mapX + gp.tileSize * 10;
        mapY = player.mapY + gp.tileSize;
        speed = 2;
        moving = true;
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
        return "resources/monsters/ghosts/ghost1.gif";
    }
}
