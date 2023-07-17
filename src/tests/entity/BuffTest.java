package tests.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import entity.Buff;

public class BuffTest {
    int threshold = 100000;
    
    @Test
    @DisplayName("Test damage buff")
    public void damageBuffTest() {
        int baseDamage = 1;
        Buff buff = new Buff(1, baseDamage);
        assertEquals("Problems with initial base damage", baseDamage, buff.getDamage());
        buff.activateDamageBuff();
        assertTrue("Damage buff not activated", buff.getDamage() > baseDamage);
        for (int i = 0; i < threshold; i++) {
            buff.getDamage();
        }
        assertEquals("Damage buff never run out", baseDamage, buff.getDamage());
    }
    
    @Test
    @DisplayName("Test speed buff")
    public void speedBuffTest() {
        int baseSpeed = 1;
        Buff buff = new Buff(baseSpeed, 1);
        assertEquals("Problems with initial base speed", baseSpeed, buff.getSpeed());
        buff.activateSpeedBuff();
        assertTrue("Speed buff not activated", buff.getSpeed() > baseSpeed);
        for (int i = 0; i < threshold; i++) {
            buff.getSpeed();
        }
        assertEquals("Speed buff never run out", baseSpeed, buff.getSpeed());
    }
}
