package entity.monsters;

import java.util.Random;

import javax.swing.ImageIcon;

import main.GamePanel;

public class Sereia extends Monsters {

    private static String basicPath = "resources/monsters/sereias/";
    private static String pinkPath = "pink/";
    private static String greenPath = "green/";
    private static String rightPath = "right.gif";
    private static String leftPath = "left.gif";
    private static String battlePath = "left.gif";
    private final String finalRightPath;
    private final String finalLeftPath;
    private static final int movingDown = 1;
    private static final int movingUp = 2;
    private static final int movingRight = 3;
    private static final int movingLeft = 4;
    private int movementDirection;
    private boolean pink; // if false, its a green mermaid

    final int tilesToMove = 5;
    int totalMoved = 0;
    boolean movingX = true;     // false means movingY
    boolean forward = true;     // false means backwards

    public Sereia(GamePanel gp, int mapX, int mapY) {
        this.gp = gp;
        Random rand = new Random();
        pink = rand.nextBoolean();
        if (pink) {
            finalLeftPath = basicPath + pinkPath + leftPath;
            finalRightPath = basicPath + pinkPath + rightPath;
        } else {
            finalLeftPath = basicPath + greenPath + leftPath;
            finalRightPath = basicPath + greenPath + rightPath;
        } 
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 4;
        mapX = gp.tileSize * 40;
        mapY = gp.tileSize;
        speed = 3;
        moving = true;
        if (pink) {
            battleImage = new ImageIcon(basicPath + pinkPath + battlePath).getImage();
        } else battleImage = new ImageIcon(basicPath + greenPath + battlePath).getImage();
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
                imagePath = finalLeftPath;
                break;

            case movingLeft:
                imagePath = finalLeftPath;
                break;

            case movingRight:
                imagePath = finalRightPath;
                break;

            case movingUp:
                imagePath = finalRightPath;
                break;

            default:
                imagePath = finalRightPath;
                break;
        }
        return imagePath;
    }
    
}
