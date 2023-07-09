package entity;

import java.awt.Graphics2D;
import java.awt.Image;

public abstract class Entity {
    public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int x;
    public int y;
    public int speed;
    public Image entityImage;

    abstract void setDefaltValues();
    abstract public void update();
    abstract public void draw(Graphics2D g2);
}
