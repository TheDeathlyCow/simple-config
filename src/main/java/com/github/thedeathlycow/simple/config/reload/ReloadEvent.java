package com.github.thedeathlycow.simple.config.reload;

import java.util.HashSet;
import java.util.Set;

/**
 * Event delegate for reloading configs.
 * Responsible to adding listeners and triggering the reload event.
 */
public class ReloadEvent {

    /**
     * Adds a listener to this reload event
     *
     * @param listener Listener to add to event
     */
    public void addListener(Reloadable listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from this reload event
     *
     * @param listener Listener to remove from event
     */
    public boolean removeListener(Reloadable listener) {
        return listeners.remove(listener);
    }

    /**
     * Trigger reload event
     */
    public void reload() {
        listeners.forEach(Reloadable::onReload);
    }

    /**
     * Set of listeners
     */
    private final Set<Reloadable> listeners = new HashSet<>();
}
