package me.timefall.timefall.sounds;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sound
{
    private Clip clip;
    private FloatControl floatControl;

    public Sound(String path)
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
            this.clip.addLineListener(event -> {
                System.out.println(event.getType().toString());
            });

            this.floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
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
}
