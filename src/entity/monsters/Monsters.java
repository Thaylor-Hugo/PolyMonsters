package entity.monsters;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.Entity;

public abstract class Monsters extends Entity {
    public int visionRange; // Range to start a battle

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        int screenX = mapX - gp.getPlayer().mapX + gp.getPlayer().screenX;
        int screenY = mapY - gp.getPlayer().mapY + gp.getPlayer().screenY;
        int centerX = screenX + gp.tileSize/2;
        int centerY = screenY + gp.tileSize/2;

        g2.setColor(new Color(255,0,0,20));
        g2.fillOval(centerX-visionRange, centerY-visionRange, visionRange*2, visionRange*2);
    }
}
