package com.github.thedeathlycow.simple.config;

import com.github.thedeathlycow.simple.config.key.ConfigEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles entry registration for a config.
 *
 * @author TheDeathlyCow
 */
public class ConfigEntryRegistry {

    /**
     * Gets all entries in this registry.
     *
     * @return Returns an unmodifiable collection of config keys.
     */
    public Collection<ConfigEntry<?>> getEntries() {
        return Collections.unmodifiableCollection(entries.values());
    }

    /**
     * Gets a config entry from a string name.
     *
     * @param name Name of config key to get.
     * @return Returns key if found, null otherwise.
     */
    @Nullable
    public ConfigEntry<?> getEntry(String name) {
        return entries.get(name);
    }

    /**
     * Adds a config entry to the registry.
     *
     * @param configEntry Entry to be added
     * @param <T> Type of the value that the config entry stores.
     * @throws IllegalArgumentException Thrown if the entry is already registered.
     */
    public <T> void register(ConfigEntry<T> configEntry) {
        if (!entries.containsKey(configEntry.getName())) {
            entries.put(configEntry.getName(), configEntry);
        } else {
            throw new IllegalArgumentException("Config entry " + configEntry.getName() + " already registered!");
        }
    }

    /**
     * Config key registry.
     */
    private final Map<String, ConfigEntry<?>> entries = new HashMap<>();
}
