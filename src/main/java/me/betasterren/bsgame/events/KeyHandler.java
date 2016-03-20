package me.betasterren.bsgame.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements KeyListener {
    private List<Listener> listeners = new ArrayList<Listener>();

    public static final int PRESSED = 0;
    public static final int RELEASED = 1;

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Keys key = getKey(e.getKeyCode());

        // Check if the key exists
        if (key == null) return;

        // Trigger event
        triggerEvent(key, PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keys key = getKey(e.getKeyCode());

        // Check if the key exists
        if (key == null) return;

        // Trigger event
        triggerEvent(key, RELEASED);
    }

    public void addListener(Listener listener) {
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    public void triggerEvent(Keys key, int eventCode) {
        for (Listener listener : listeners)
            listener.onKeyEvent(key, eventCode);
    }

    public Keys getKey(int keyID) {
        for (Keys key : Keys.values())
            if (key.getKeyID() == keyID) return key;
        return null;
    }
}
