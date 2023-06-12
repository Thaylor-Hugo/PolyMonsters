package entity;

import java.awt.Graphics2D;
import java.awt.Image;

public abstract class Entity {
    public int mapX;    //position related to the world map
    public int mapY;
    public int speed;
    public Image entityImage;

    abstract void setDefaltValues();
    abstract public void update();
    abstract public void draw(Graphics2D g2);
}
