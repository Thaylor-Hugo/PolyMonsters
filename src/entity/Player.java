package entity;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        setDefaltValues();
        getPlayerImage();
        this.gp = gp;
        this.keyH = keyH;
    }

    public void getPlayerImage() {
        try {
            entityImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player.png"));
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
    }
    
    @Override
    public void update() {
        if (keyH.downPressed) {
            y += speed;
        }
        if (keyH.leftPressed) {
            x -= speed;
        }
        if (keyH.rightPressed) {
            x += speed;   
        }
        if (keyH.upPressed) {
            y -= speed;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(entityImage, x, y, gp.tileSize, gp.tileSize, null);
    }
    
}
