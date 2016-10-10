package me.timefall.timefall.sounds.components;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sound extends BigClip
{
    protected SoundCharacteristic[] soundCharacteristics;

    public Sound(String path, SoundCharacteristic[] soundCharacteristics)
    {
        try
        {
            this.soundCharacteristics = soundCharacteristics;
            InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInput = new BufferedInputStream(inputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInput);

            super.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    public SoundCharacteristic[] getSoundCharacteristics()
    {
        return soundCharacteristics;
    }
}
