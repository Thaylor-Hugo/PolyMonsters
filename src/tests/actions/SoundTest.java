package tests.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import actions.Sound;

public class SoundTest {
    // Test might fail if the path is changed
    static String path = "/music/battle.wav";

    @Test
    @DisplayName("Test if sound is running")
    public void isRunningTest() throws InterruptedException {
        Sound sound = new Sound(getClass().getResource(path));
        sound.play();
        Thread.sleep(100); // Fail if tested without sleep
        assertTrue("Sound don't run when tested", sound.isRunning());
    }
    
    @Test
    @DisplayName("Test if sound stop")
    public void stopTest() throws InterruptedException {
        Sound sound = new Sound(getClass().getResource(path));
        sound.play();
        Thread.sleep(100);
        if (sound.isRunning()) {
            sound.stop();
            assertFalse("Sound don't stop when method is called", sound.isRunning());
        } else {
            assertTrue("Sound dint start whe method was called", false);
        }
    }
}
