package me.timefall.timefall.game.game;

import me.timefall.timefall.events.keys.KeyHandler;
import me.timefall.timefall.events.keys.Keys;
import me.timefall.timefall.events.Listener;

public class MoveListener implements Listener
{
    @Override
    public void onKeyEvent(Keys key, int evenCode)
    {
        // Check if the key was pressed or released and take appropriate action
        switch (evenCode)
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
