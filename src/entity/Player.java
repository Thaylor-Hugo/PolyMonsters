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

    GamePanel gp;
    KeyHandler keyH;
    public boolean sprinting;
    public boolean moving;
    public int movementDirection;
    public boolean nearBike;
    public boolean onBike;
    public long lastInteraction = System.nanoTime();

    public Player(GamePanel gp, KeyHandler keyH) { //TODO implementar Singleton
        setDefaltValues();
        getPlayerImage(sprinting, movementDirection);
        this.gp = gp;
        this.keyH = keyH;
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
        x = 100;
        y = 100;
        speed = 2;
        sprinting = false;
        moving = false;
        movementDirection = movingDown;
        onBike = false;
        nearBike = false;
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
        } 
        
        else if (keyH.interactPressed && this.onBike) {
        	double delta = System.nanoTime() - this.lastInteraction;
        	System.out.println("DELTA INTERACTION DISMOUNT "+delta);
        	this.lastInteraction = System.nanoTime();
        	if(delta>1E8) {
                this.onBike = false;
        	}
        } 
        else if (keyH.interactPressed && this.nearBike) {
        	double delta = System.nanoTime() - this.lastInteraction;
        	System.out.println("DELTA INTERACTION MOUNT "+delta);
        	this.lastInteraction = System.nanoTime();
        	if(delta>1E8) {
                this.onBike = true;
        	}
        } 
        
        else {
            moving = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
    	if(this.onBike) {
    		
    	}
    	else {
    		getPlayerImage(sprinting, movementDirection);
            g2.drawImage(entityImage, x, y, gp.tileSize, gp.tileSize, null);
    	}
        
    }
    
    

	@Override
	public String toString() {
		return "Player [keyH=" + keyH + "\nsprinting=" + sprinting + "\nmoving=" + moving
				+ "\n movementDirection=" + movementDirection + "\nnearBike=" + nearBike + "\nonBike=" + onBike + "\nx="
				+ x + "\ny=" + y + "\nspeed=" + speed + "]";
	}

	public boolean isNearBike() {
		return nearBike;
	}

	public void setNearBike(boolean nearBike) {
		this.nearBike = nearBike;
	}

	public boolean isOnBike() {
		return onBike;
	}

	public void setOnBike(boolean onBike) {
		this.onBike = onBike;
	}
    
    
    
    
    
}
