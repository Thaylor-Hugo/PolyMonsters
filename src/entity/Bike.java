package entity;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.GamePanel;
import main.KeyHandler;

public class Bike extends Entity {
//
//    private static String walkingDownPath = "resources/player/movement/walking_down.gif";
//    private static String walkingUpPath = "resources/player/movement/walking_up.gif";
//    private static String walkingRightPath = "resources/player/movement/walking_right.gif";
//    private static String walkingLeftPath = "resources/player/movement/walking_left.gif";
//    private static String sprintingDownPath = "resources/player/movement/sprinting_down.gif";
//    private static String sprintingUpPath = "resources/player/movement/sprinting_up.gif";
//    private static String sprintingRightPath = "resources/player/movement/sprinting_right.gif";
//    private static String sprintingLeftPath = "resources/player/movement/sprinting_left.gif";

    private static String walkingDownPath = "resources/bike/movement/player_on_bike.gif";
    private static String walkingUpPath = "resources/bike/movement/player_on_bike.gif";
    private static String walkingRightPath = "resources/bike/movement/player_on_bike.gif";
    private static String walkingLeftPath = "resources/bike/movement/player_on_bike.gif";
    private static String sprintingDownPath = "resources/bike/movement/player_on_bike.gif";
    private static String sprintingUpPath = "resources/bike/movement/player_on_bike.gif";
    private static String sprintingRightPath = "resources/bike/movement/player_on_bike.gif";
    private static String sprintingLeftPath = "resources/bike/movement/player_on_bike.gif";
    private static String parkedWithPlayerPath = "resources/bike/movement/player_on_bike.gif";
    private static String parkedWithOutPlayerPath = "resources/bike/movement/base.gif";
    private static final int movingDown = 1;
    private static final int movingUp = 2;
    private static final int movingRight = 3;
    private static final int movingLeft = 4;
    private static final int parked = 0;

    GamePanel gp;
    KeyHandler keyH;
    public boolean sprinting;
    public boolean moving;
    public boolean withPlayer;
    public Entity ridingPlayer;
    public int movementDirection;
    

    public Bike(GamePanel gp, KeyHandler keyH) { 
        setDefaltValues();
        getBikeImage(sprinting,withPlayer, movementDirection);
        this.gp = gp;
        this.keyH = keyH;
        this.ridingPlayer = null;
        this.withPlayer = false;
    }

    private void getBikeImage(boolean sprinting,boolean withPlayer,  int movementDirection) {
        String imagePath = getImagePath(sprinting, withPlayer, movementDirection);
        if (moving) entityImage = new ImageIcon(imagePath).getImage();
        else
            try {
                entityImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    private String getImagePath(boolean sprinting, boolean withPlayer, int movementDirection) {
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
                if (withPlayer) imagePath = parkedWithPlayerPath;
                else imagePath = parkedWithOutPlayerPath;
                break;
        }
        return imagePath;
    }
    
    @Override
    void setDefaltValues() {
        x = 200;
        y = 100;
        speed = 5;
        sprinting = false;
        moving = false;
        movementDirection = parked;
    }
    
    @Override
    public void update( ) {
    	if(withPlayer) {
    		int finalSpeed = speed;
            if (keyH.sprintPressed) {
                sprinting = true;
                finalSpeed += 5;
            } else {
                sprinting = false;
            }
            moving = true;
            if (keyH.downPressed) {
                y += finalSpeed;
                movementDirection = movingDown;
            } else if (keyH.leftPressed) {
                x -= finalSpeed;
                movementDirection = movingLeft;
            } else if (keyH.rightPressed) {
                x += finalSpeed;   
                movementDirection = movingRight;
            } else if (keyH.upPressed) {
                y -= finalSpeed;
                movementDirection = movingUp;
            } else {
                moving = false;
                movementDirection = parked;
            }
            this.ridingPlayer.setX(this.x);
            this.ridingPlayer.setY(this.y);
    	}
        
    }

    @Override
    public void draw(Graphics2D g2) {
        getBikeImage(sprinting, withPlayer, movementDirection);
        g2.drawImage(entityImage, x, y, gp.tileSize, gp.tileSize, null);
    }
    
    
    

	@Override
	public String toString() {
		return "Bike [keyH=" + keyH + "\nsprinting=" + sprinting + "\nmoving=" + moving + "\nwithPlayer="
				+ withPlayer + "\nridingPlayer=" + ridingPlayer + "\nmovementDirection=" + movementDirection + "\nx="
				+ x + "\ny=" + y + "\nspeed=" + speed + "]";
	}

	public boolean isWithPlayer() {
		return withPlayer;
	}

	public void setWithPlayer(boolean withPlayer) {
		this.withPlayer = withPlayer;
	}

	public Entity getRidingPlayer() {
		return ridingPlayer;
	}

	public void setRidingPlayer(Entity ridingPlayer) {
		this.ridingPlayer = ridingPlayer;
	}
    
	
    
}
