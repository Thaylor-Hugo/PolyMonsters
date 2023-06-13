package entity;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    private static String walkingDownPath = "resources/player/movement/walking_down.gif";
    private static String walkingUpPath = "resources/player/movement/walking_up.gif";
    private static String walkingRightPath = "resources/player/movement/walking_right.gif";
    private static String walkingLeftPath = "resources/player/movement/walking_left.gif";
    private static String sprintingDownPath = "resources/player/movement/sprinting_down.gif";
    private static String sprintingUpPath = "resources/player/movement/sprinting_up.gif";
    private static String sprintingRightPath = "resources/player/movement/sprinting_right.gif";
    private static String sprintingLeftPath = "resources/player/movement/sprinting_left.gif";
    private static final int movingDown = 1;
    private static final int movingUp = 2;
    private static final int movingRight = 3;
    private static final int movingLeft = 4;

    //player position related to the screen
    public final int screenX;
    public final int screenY;

    GamePanel gp;
    KeyHandler keyH;
    public boolean sprinting;
    public boolean moving;
    public int movementDirection;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        //player's position fixed in the center of screen
        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        setDefaltValues();
        getPlayerImage(sprinting, movementDirection);
    }

    private void getPlayerImage(boolean sprinting, int movementDirection) {
        String imagePath = getImagePath(sprinting, movementDirection);
        if (moving) entityImage = new ImageIcon(imagePath).getImage();
        else
            try {
                entityImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    private String getImagePath(boolean sprinting, int movementDirection) {
        String imagePath;
        switch (movementDirection) {
            case movingDown:
                if (sprinting) imagePath = sprintingDownPath;
                else imagePath = walkingDownPath;   
                break;

            case movingLeft:
                if (sprinting) imagePath = sprintingLeftPath;
                else imagePath = walkingLeftPath;
                break;

            case movingRight:
                if (sprinting) imagePath = sprintingRightPath;
                else imagePath = walkingRightPath;
                break;

            case movingUp:
                if (sprinting) imagePath = sprintingUpPath; 
                else imagePath = walkingUpPath; 
                break;

            default:
                if (sprinting) imagePath = sprintingDownPath;
                else imagePath = walkingDownPath;
                break;
        }
        return imagePath;
    }
    
    @Override
    void setDefaltValues() {
        mapX = gp.tileSize*36;     //position related to the world map
        mapY = gp.tileSize*84/2;
        speed = 2;
        sprinting = false;
        moving = false;
        movementDirection = movingDown;
    }
    
    @Override
    public void update() {
        int finalSpeed = speed;
        if (keyH.sprintPressed) {
            sprinting = true;
            finalSpeed += 2;
        } else {
            sprinting = false;
        }
        moving = true;
        if (keyH.downPressed) {
            mapY += finalSpeed;
            movementDirection = movingDown;
        } else if (keyH.leftPressed) {
            mapX -= finalSpeed;
            movementDirection = movingLeft;
        } else if (keyH.rightPressed) {
            mapX += finalSpeed;   
            movementDirection = movingRight;
        } else if (keyH.upPressed) {
            mapY -= finalSpeed;
            movementDirection = movingUp;
        } else {
            moving = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        getPlayerImage(sprinting, movementDirection);
        g2.drawImage(entityImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
    
}
