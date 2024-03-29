package entity.objects;

import entity.Entity;
import entity.Player;
import main.KeyHandler;

/**
 * Represent a interrectable object in the game world
 */
public abstract class Object extends Entity {
    int interrectRange;
    boolean inInterection;
    Player player;
    KeyHandler keyH;

    /**
     * Check if player is close enough to interrect with object
     * @return If player is inside interrection range
     */
    protected boolean inRange() {
        int distanceX = (mapX + (gp.tileSize / 2)) - (player.mapX + (gp.tileSize / 2));
        int distanceY = (mapY + (gp.tileSize / 2)) - (player.mapY + (gp.tileSize / 2));
        if (distanceX < 0) distanceX = distanceX * -1;
        if (distanceY < 0) distanceY = distanceY * -1;
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        
        if (distance <= interrectRange) return true;
        return false;
    }
}
