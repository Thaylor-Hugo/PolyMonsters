package actions;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

// Sounds used from pixabay
public class Sound {

    Clip clip;
    float currentVolume = 0;
    FloatControl fc;

    public Sound(URL url) {
        setFile(url);
    }
    
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

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void setVolume(float volume) {
        if (volume > 6.0f) volume = 6.0f;
        if (volume < -80.0f) volume = -80.0f;
        currentVolume = volume;
        fc.setValue(currentVolume);
    }

    public boolean isRunning() {
        return clip.isRunning();
    }
}
