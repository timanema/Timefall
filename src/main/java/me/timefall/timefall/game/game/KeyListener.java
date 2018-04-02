package me.timefall.timefall.game.game;

import me.timefall.timefall.events.keys.KeyHandler;
import me.timefall.timefall.events.keys.Keys;
import me.timefall.timefall.events.Listener;

public class KeyListener implements Listener
{
    @Override
    public void onKeyEvent(Keys key, int eventCode)
    {
        // Check if the key was pressed or released and take appropriate action
        switch (eventCode)
        {
            case KeyHandler.PRESSED:
                key.togglePressed(true);
                break;
            case KeyHandler.RELEASED:
                key.togglePressed(false);
                break;
            default:
                break;
        }
    }
}
