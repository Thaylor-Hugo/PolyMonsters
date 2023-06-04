package entity;

import java.awt.Graphics2D;
import java.awt.Image;

public abstract class Entity {
    public int x;
    public int y;
    public int speed;
    public Image entityImage;

    abstract void setDefaltValues();
    abstract public void update();
    abstract public void draw(Graphics2D g2);
}
