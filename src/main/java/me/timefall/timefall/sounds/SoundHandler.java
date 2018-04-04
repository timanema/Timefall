package me.timefall.timefall.sounds;

import me.timefall.timefall.sounds.components.Music;
import me.timefall.timefall.threads.GameThread;

import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class SoundHandler
{
    private HashMap<Music, Float> currentSounds;
    private ArrayList<Music> fadeOut;
    private boolean soundsDisabled;

    public SoundHandler(boolean soundsDisabled)
    {
        this.soundsDisabled = soundsDisabled;
        this.currentSounds = new HashMap<>();
        this.fadeOut = new ArrayList<>();

        if (soundsDisabled)
        {
            System.out.println("[TimeFall] Sounds are now disabled.");
            return;
        }

        this.initSounds();
    }

    private void initSounds()
    {
        // Add more things
    }

    public void tick()
    {
        for (Music music : this.currentSounds.keySet())
        {
            if (this.fadeOut.contains(music))
            {
                this.currentSounds.put(music, this.currentSounds.get(music) - (1F / (GameThread.TICKS * 2.4F)));
            }

            if (this.currentSounds.get(music) <= 0.0)
            {
                this.currentSounds.remove(music);
                this.fadeOut.remove(music);
                music.stop();
                continue;
            }

            FloatControl control = ((FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN));

            control.setValue((control.getMaximum() - control.getMinimum()) * this.currentSounds.get(music) + control.getMinimum());
        }
    }

    public void switchScreen()
    {
        // Terminate current tracks
        this.fadeOut.addAll(this.currentSounds.keySet());
    }

    public void startMusicTitleScreen()
    {
        if (soundsDisabled)
        {
            return;
        }

        Music startupSound = new Music("/music/startup.wav", null);
        FloatControl control = ((FloatControl) startupSound.getControl(FloatControl.Type.MASTER_GAIN));

        this.currentSounds.put(startupSound, 1.0F);
        startupSound.start();
        control.setValue(control.getMaximum());
    }

    public void triggerLineListener(LineEvent lineEvent)
    {
        System.out.println("LineEvent: " + lineEvent.getType());
    }
}
