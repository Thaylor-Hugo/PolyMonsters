package actions;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

// Sounds used from pixabay

/**
 * Class represent a playable {@code Sound}
 */
public class Sound {

    Clip clip;
    float currentVolume = 0;
    FloatControl fc;

    public Sound(URL url) {
        setFile(url);
    }
    
    /**
     * Set the sound file
     * @param url Path to file
     */
    public void setFile(URL url) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(sound);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Play the sound
     */
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Play sound on loop
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stop played sound
     */
    public void stop() {
        clip.stop();
    }

    /**
     * Set sound volume 
     * @param volume Target volume, between -80.0 and 6.0 inclusive
     */
    public void setVolume(float volume) {
        if (volume > 6.0f) volume = 6.0f;
        if (volume < -80.0f) volume = -80.0f;
        currentVolume = volume;
        fc.setValue(currentVolume);
    }

    /**
     * Check id sound is current being played
     * @return If sound is beign played
     */
    public boolean isRunning() {
        return clip.isRunning();
    }
}
