package entity;

public class Buff {
    int baseSpeed, baseDamage;
    int speedBuffTime = 60 * 60; // 1 minute
    int damageBuffrounds = 5;
    final int damageBuff = 0;
    final int speedBuff = 1;
    int currentBuffTime[] = {0, 0};
    boolean damageBuffOn, speedBuffOn;
    
    public Buff(int baseSpeed, int baseDamage) {
        this.baseDamage = baseDamage;
        this.baseSpeed = baseSpeed;
        damageBuffOn = false;
        speedBuffOn = false;
    }

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

    public void activateDamageBuff() {
        damageBuffOn = true;
    }

    public void activateSpeedBuff() {
        speedBuffOn = true;
    }

}
