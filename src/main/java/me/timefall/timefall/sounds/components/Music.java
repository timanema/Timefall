package me.timefall.timefall.sounds.components;

import me.timefall.timefall.Timefall;

public class Music extends Sound
{
    public Music(String path, SoundCharacteristic[] soundCharacteristics)
    {
        super(path, soundCharacteristics);
        super.addLineListener(event -> Timefall.getSoundHandler().triggerLineListener(event));
    }
}
