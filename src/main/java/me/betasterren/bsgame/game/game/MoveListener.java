package me.betasterren.bsgame.game.game;

import me.betasterren.bsgame.events.KeyHandler;
import me.betasterren.bsgame.events.Keys;
import me.betasterren.bsgame.events.Listener;

public class MoveListener implements Listener {
    @Override
    public void onKeyEvent(Keys key, int evenCode) {
        switch (evenCode) {
            case KeyHandler.PRESSED:
                key.togglePressed(true);
                break;
            case KeyHandler.RELEASED:
                key.togglePressed(false);
                break;
            default:
                break;
        }

        // TODO: Remove debug code when done
        System.out.println(key.isPressed());
    }
}
