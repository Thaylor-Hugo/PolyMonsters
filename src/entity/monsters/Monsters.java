package entity.monsters;

import java.awt.Color;
import java.awt.Graphics2D;

import actions.Sound;
import entity.Entity;

/**
 * Class represents a hostile monster
 */
public abstract class Monsters extends Entity {
    public int visionRange; // Range to start a battle
    Sound sound;

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

    /**
     * Play the monster sound. Volume is defined by distance to player
     */
    protected void playSound() {
        int distanceX = (mapX + (gp.tileSize / 2)) - (gp.getPlayer().mapX + (gp.tileSize / 2));
        int distanceY = (mapY + (gp.tileSize / 2)) - (gp.getPlayer().mapY + (gp.tileSize / 2));
        if (distanceX < 0) distanceX = distanceX * -1;
        if (distanceY < 0) distanceY = distanceY * -1;
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        
        float volume = 6.0f - (float) distance * 0.1f;
        sound.setVolume(volume);
        if (!sound.isRunning()) sound.play();
    }
}
