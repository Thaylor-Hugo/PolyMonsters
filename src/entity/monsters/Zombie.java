package entity.monsters;

import java.util.Random;

import javax.swing.ImageIcon;

import main.GamePanel;

public class Zombie extends Monsters {

    private static String basicPath = "resources/monsters/zombies/";
    private static String mostAlivePath = "mostAlive/";
    private static String mostDeadPath = "mostDead/";
    private static String rightPath = "right.gif";
    private static String leftPath = "left.gif";
    private static String upPath = "up.gif";
    private static String downPath = "down.gif";
    private static String battlePathMostAlive = "resources/battle/zombies/mostAlive.gif";
    private static String battlePathMostDead = "resources/battle/zombies/mostDead.gif";
    private final String finalRightPath;
    private final String finalLeftPath;
    private final String finalUpPath;
    private final String finalDownPath;
    private static final int movingDown = 1;
    private static final int movingUp = 2;
    private static final int movingRight = 3;
    private static final int movingLeft = 4;
    private int movementDirection;
    private boolean mostDead; // if false, its a mostAlive zombie

    final int tilesToMove = 5;
    int totalMoved = 0;
    boolean movingX = true;     // false means movingY
    boolean forward = true;     // false means backwards

    public Zombie(GamePanel gp, int mapX, int mapY) {
        this.gp = gp;
        Random rand = new Random();
        mostDead = rand.nextBoolean();
        if (mostDead) {
            finalLeftPath = basicPath + mostDeadPath + leftPath;
            finalRightPath = basicPath + mostDeadPath + rightPath;
            finalUpPath = basicPath + mostDeadPath + upPath;
            finalDownPath = basicPath + mostDeadPath + downPath;
        } else {
            finalLeftPath = basicPath + mostAlivePath + leftPath;
            finalRightPath = basicPath + mostAlivePath + rightPath;
            finalUpPath = basicPath + mostAlivePath + upPath;
            finalDownPath = basicPath + mostAlivePath + downPath;
        } 
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 4;
        mapX = gp.tileSize * 50;
        mapY = gp.tileSize;
        moving = true;
        if (mostDead) {
            battleImage = new ImageIcon(battlePathMostDead).getImage();
            speed = 1;
            hp = 60;
            damage = 6;
        } else {
            battleImage = new ImageIcon(battlePathMostAlive).getImage();
            speed = 2;
            hp = 80;
            damage = 8;
        }
    }

    @Override
    public int getRefHp() {
        return 100;
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
                imagePath = finalDownPath;
                break;

            case movingLeft:
                imagePath = finalLeftPath;
                break;

            case movingRight:
                imagePath = finalRightPath;
                break;

            case movingUp:
                imagePath = finalUpPath;
                break;

            default:
                imagePath = finalDownPath;
                break;
        }
        return imagePath;
    }
    
}
