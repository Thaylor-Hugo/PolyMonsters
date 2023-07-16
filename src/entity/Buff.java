package entity;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * A entity {@code Buff} that controls the speed and damage buffs and animations 
 */
public class Buff {
    int baseSpeed, baseDamage;
    int speedBuffTime = 60 * 60; // 1 minute
    int hpCureAnimation = 60 * 1; // 5 seconds
    int damageBuffrounds = 5;
    final int damageBuff = 0;
    final int speedBuff = 1;
    final int hpCureBuff = 2;
    int currentBuffTime[] = {0, 0, 0};
    boolean damageBuffOn, speedBuffOn, hpCureBuffOn;
    Image damageImage, speedImage, hpCureImage;
    
    public Buff(int baseSpeed, int baseDamage) {
        this.baseDamage = baseDamage;
        this.baseSpeed = baseSpeed;
        damageBuffOn = false;
        speedBuffOn = false;
        hpCureBuffOn = false;
        damageImage = new ImageIcon("resources/buff/damage.gif", null).getImage();
        speedImage = new ImageIcon("resources/buff/speed.gif", null).getImage();
        hpCureImage = new ImageIcon("resources/buff/health.gif", null).getImage();
    }

    /**
     * Get the buff damage
     * @return Damage
     */
    public double getDamage() {
        int finalDamage = baseDamage;
        if (damageBuffOn) {
            finalDamage += 5;
            currentBuffTime[damageBuff]++;
            if (currentBuffTime[damageBuff] == damageBuffrounds) {
                damageBuffOn = false;
                currentBuffTime[damageBuff] = 0;
            }
        }
        return finalDamage;
    }

    /**
     * Get teh buff speed
     * @return Speed
     */
    public int getSpeed() {
        int finalSpeed = baseSpeed;
        if (speedBuffOn) {
            finalSpeed += 5;
            currentBuffTime[speedBuff]++;
            if (currentBuffTime[speedBuff] == speedBuffTime) {
                speedBuffOn = false;
                currentBuffTime[speedBuff] = 0;
            }
        }
        return finalSpeed;
    }

    /**
     * Activate a damage buff
     */
    public void activateDamageBuff() {
        damageBuffOn = true;
    }
    
    /**
     * Activate a speed buff
     */
    public void activateSpeedBuff() {
        speedBuffOn = true;
    }
    
    /**
     * Draw the buff animation
     */
    public void draw(Graphics2D g2, int x, int y, int tileSize, boolean inBattle) {
        if (damageBuffOn && inBattle) {
            g2.drawImage(damageImage, x, y, tileSize, tileSize, null);
        }
        if (speedBuffOn && !inBattle){
            g2.drawImage(speedImage, x, y, tileSize, tileSize, null);
        }
        if (hpCureBuffOn) {
            currentBuffTime[hpCureBuff]++;
            if (currentBuffTime[hpCureBuff] == hpCureAnimation) {
                hpCureBuffOn = false;
                currentBuffTime[hpCureBuff] = 0;
            }
            g2.drawImage(hpCureImage, x, y, tileSize, tileSize, null);
        }
    }

    /**
     * Activate a hp cure
     */
    public void activateHpCure() {
        hpCureBuffOn = true;
    }
}
