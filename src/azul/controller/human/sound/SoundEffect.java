package azul.controller.human.sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect
{

    Clip clip;
    AudioInputStream audioInputStream;
    static String filePath="res/sounds/taketile.wav";

    // constructor to initialize streams and clip
    public SoundEffect() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }


    public void play()
    {
        clip.setMicrosecondPosition(0);
        clip.start();

    }


}
