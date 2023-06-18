package entity.monsters;

import entity.Player;
import main.GamePanel;

public class Rat extends Monsters {
    private static String downPath = "resources/monsters/rats/rat_down.gif";
    private static String upPath = "resources/monsters/rats/rat_up.gif";
    private static String rightPath = "resources/monsters/rats/rat_right.gif";
    private static String leftPath = "resources/monsters/rats/rat_left.gif";
    private static final int movingDown = 1;
    private static final int movingUp = 2;
    private static final int movingRight = 3;
    private static final int movingLeft = 4;
    private int movementDirection;

    final int tilesToMove = 5;
    int totalMoved = 0;
    boolean movingX = true;     // false means movingY
    boolean forward = true;     // false means backwards

    public Rat(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        setDefaltValues();
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 1;
        // TODO need to change defaut position
        mapX = player.mapX + gp.tileSize;
        mapY = player.mapY + gp.tileSize;
        speed = 3;
        moving = true;
    }

    @Override
    public void update() {
        // TODO better moviment strategy
        int totalToMove = tilesToMove * gp.tileSize;
        if (movingX) {
            if (forward) {
                mapX += speed;
                movementDirection = movingRight;
            }
            else {
                mapX -= speed;
                movementDirection = movingLeft;
            }
            totalMoved += speed;
            
        } else {
            if (forward) {
                mapY += speed;
                movementDirection = movingDown;
            }
            else {
                mapY -= speed;
                movementDirection = movingUp;
            } 
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
        String imagePath;
        switch (movementDirection) {
            case movingDown:
                imagePath = downPath;
                break;

            case movingLeft:
                imagePath = leftPath;
                break;

            case movingRight:
                imagePath = rightPath;
                break;

            case movingUp:
                imagePath = upPath;
                break;

            default:
                imagePath = rightPath;
                break;
        }
        return imagePath;
    }
    
}
