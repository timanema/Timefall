package me.timefall.timefall.events;

import me.timefall.timefall.events.keys.Keys;

public interface Listener
{
    void onKeyEvent(Keys key, int eventCode);
}
