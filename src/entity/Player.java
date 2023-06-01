package entity;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    boolean sprinting;

    public Player(GamePanel gp, KeyHandler keyH) {
        setDefaltValues();
        getPlayerImage(sprinting);
        this.gp = gp;
        this.keyH = keyH;
    }

    public void getPlayerImage(boolean sprinting) {
        try {
            if (sprinting) {
                entityImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/movement/sprinting_down.gif"));
            } else {
                entityImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/movement/walking_down.gif"));   
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    @Override
    void setDefaltValues() {
        x = 100;
        y = 100;
        speed = 4;
        sprinting = false;
    }
    
    @Override
    public void update() {
        int finalSpeed = speed;
        if (keyH.sprintPressed) {
            sprinting = true;
            finalSpeed += 2;
        }
        if (keyH.downPressed) {
            y += finalSpeed;
        } else if (keyH.leftPressed) {
            x -= finalSpeed;
        } else if (keyH.rightPressed) {
            x += finalSpeed;   
        } else if (keyH.upPressed) {
            y -= finalSpeed;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        getPlayerImage(sprinting);
        g2.drawImage(entityImage, x, y, gp.tileSize, gp.tileSize, null);
    }
    
}
