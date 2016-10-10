package me.timefall.timefall.sounds;

import me.timefall.timefall.sounds.components.Music;

import javax.sound.sampled.LineEvent;

public class SoundHandler
{
    public SoundHandler(boolean soundsDisabled)
    {
        if (soundsDisabled)
        {
            System.out.println("[TimeFall] Sounds are now disabled.");
            return;
        }

        this.initSounds();
    }

    private void initSounds()
    {
        new Music("/sounds/music/far_horizons.wav", null).start();
    }

    public void triggerLineListener(LineEvent lineEvent)
    {
        System.out.println("LineEvent: " + lineEvent.getType());
    }
}
