package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.GamePanel;

public abstract class Entity {
    public int mapX;    //position related to the world map
    public int mapY;
    public int speed;
    public boolean moving;
    public Image entityImage;
    GamePanel gp;

    abstract protected void setDefaltValues();
    abstract public void update();
    abstract protected String getEntityImagePath();
    
    protected Image getEntityImage() {
        String imagePath = getEntityImagePath();
        if (moving) entityImage = new ImageIcon(imagePath).getImage();
        else try {
                entityImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entityImage;
    }

    public void draw(Graphics2D g2) {
        getEntityImage();
        g2.drawImage(entityImage, mapX, mapY, gp.tileSize, gp.tileSize, null);
    }
}
