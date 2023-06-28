package entity;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
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
    private static String battlePath = "resources/battle/player.gif";

    //player position related to the screen
    public final int screenX;
    public final int screenY;

    KeyHandler keyH;
    public boolean sprinting;

    private final int safeDistance;
    private int posibleSafeX;
    private int posibleSafeY;
    private int lastSafeX;
    private int lastSafeY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        //player's position fixed in the center of screen
        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        setDefaltValues();
        safeDistance = gp.tileSize * 5;
    }
    
    @Override
    protected void setDefaltValues() {
        mapX = gp.tileSize*36;     //position related to the world map
        mapY = gp.tileSize*84/2;
        speed = 2;
        sprinting = false;
        moving = false;
        mvDirect = MovementDirection.DOWN;
        lastSafeX = mapX;
        lastSafeY = mapY;
        posibleSafeX = mapX;
        posibleSafeY = mapY;
        battleImage = new ImageIcon(battlePath).getImage();
        hp = 100;
        damage = 10;
        setMovementStrategy(MovementTypes.CONTROLED, 0, keyH);
    }

    @Override
    public int getRefHp() {
        return 100;
    }
    
    @Override
    public void update() {
        if (keyH.sprintPressed) {
            sprinting = true;
            speed += 2;
        } else sprinting = false;
        
        mvStrategy.move(this);
        setLastSafePosition();
        if (sprinting) {
            speed -= 2;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        entityImage = getEntityImage();
        g2.drawImage(entityImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    @Override
    protected String getEntityImagePath() {
        String imagePath;
        switch (mvDirect) {
            case DOWN:
                if (sprinting) imagePath = sprintingDownPath;
                else imagePath = walkingDownPath;   
                break;

            case LEFT:
                if (sprinting) imagePath = sprintingLeftPath;
                else imagePath = walkingLeftPath;
                break;

            case RIGHT:
                if (sprinting) imagePath = sprintingRightPath;
                else imagePath = walkingRightPath;
                break;

            case UP:
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

    public void setOnLastSafePosition() {
        mapX = lastSafeX;
        mapY = lastSafeY;
    }

    private void setLastSafePosition() {
        if (Math.abs(mapX - posibleSafeX) >= safeDistance) {
            lastSafeX = posibleSafeX;
            posibleSafeX = mapX;
        }
        if (Math.abs(mapY - posibleSafeY) >= safeDistance) {
            lastSafeY = posibleSafeY;
            posibleSafeY = mapY;
        }
    }
}
