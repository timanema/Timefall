package me.betasterren.bsgame.events;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Set;

public class KeyHandler {
    private Set<Object> listeners;

    public KeyHandler(Set<Object> listeners) {
        this.listeners = listeners;
    }

    public Set<Class<?>> findAllListeners() {
        final Reflections reflections = new Reflections("org.projectx", new TypeAnnotationsScanner());
        Set<Class<?>> allMessageDrivens = reflections.getTypesAnnotatedWith(KeyListener.class);
        return allMessageDrivens;
    }

    public void runEvent() {
        Set<Class<?>> possibleListeners = findAllListeners();

        for (Object o : listeners) {
            if (possibleListeners.contains(o.getClass())) {
                // TODO: komt nog
            }
        }
    }
}
