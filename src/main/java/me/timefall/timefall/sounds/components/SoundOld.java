package me.timefall.timefall.sounds.components;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @deprecated Use {@link me.timefall.timefall.sounds.components.Sound ()}
 */
@Deprecated
public class SoundOld
{
    protected Clip clip;
    protected FloatControl floatControl;

    protected SoundCharacteristic[] soundCharacteristics;

    public SoundOld(String path, SoundCharacteristic[] soundCharacteristics)
    {
        try
        {
            InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInput = new BufferedInputStream(inputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInput);
            AudioFormat audioFormat = audioInputStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16, audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(), false);
            AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
            this.clip = AudioSystem.getClip();

            this.clip.open(decodedAudioInputStream);

            this.floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }

        this.soundCharacteristics = soundCharacteristics;
    }

    public void playSound()
    {
        if (this.clip == null)
        {
            return;
        }

        this.stopSound();
        this.clip.setFramePosition(0);

        while (!this.clip.isRunning())
        {
            this.clip.start();
        }
    }

    public void stopSound()
    {
        if (this.clip.isRunning())
        {
            this.clip.stop();
        }
    }

    public void flushSound()
    {
        this.stopSound();
        this.clip.drain();
        this.clip.close();
    }

    /**
     * Loops the clip so its state will change.
     * The listeners, therefore, will not work properly in this method.
     *
     * @deprecated Use multiple {@link #playSound()}'s for the time being instead.
     */
    @Deprecated
    public void loopSound(int loops)
    {
        this.clip.loop(loops);

        while (!this.clip.isRunning())
        {
            this.clip.start();
        }
    }

    public SoundCharacteristic[] getSoundCharacteristics()
    {
        return this.soundCharacteristics;
    }
}
