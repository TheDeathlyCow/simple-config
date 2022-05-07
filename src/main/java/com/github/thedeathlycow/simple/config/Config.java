package com.github.thedeathlycow.simple.config;
import com.github.thedeathlycow.simple.config.entry.ConfigEntry;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Base config class. Provides methods for adding keys,
 * setting values, getting values, and reload and update
 * methods for datapacks.
 */
public class Config {

    public Config(@NotNull Identifier identifier, Path parentDirectory) {

        this.identifier = identifier;
        this.parentDirectory = parentDirectory;
    }

    /**
     * Creates a temporary config with the same location as the main config
     *
     * @param mainConfig Main config to copy the location of.
     * @return Returns a new, empty config with the same location as the main config.
     */
    public static Config createTempConfig(Config mainConfig) {
        return new Config(mainConfig.identifier, mainConfig.parentDirectory);
    }

    /**
     * Gets the value of an entry.
     *
     * @param entry The entry in the config to get the value of.
     * @param <T> The type of object stored at that entry.
     * @return Returns the value stored at the entry.
     * @throws IllegalArgumentException Thrown if the entry is not
     * part of this config.
     */
    public <T> T get(ConfigEntry<T> entry) {
        if (this.values.containsKey(entry)) {
            Object value = this.values.get(entry);
            return entry.getType().cast(value);
        } else {
            throw new IllegalArgumentException("Cannot get value of " + entry + " as it does not exist in config");
        }
    }

    /**
     * Adds an entry to this config, and sets it to the default
     * value. If duplicate values are
     *
     * @param entry The entry to add.
     * @param <T> The type that the entry stores.
     * @throws IllegalArgumentException Thrown if attempting to add duplicate keys.
     */
    public <T> void addEntry(ConfigEntry<T> entry) {
        if (!this.values.containsKey(entry)) {
            entries.register(entry);
            this.values.put(entry, entry.getDefaultValue());
        } else {
            throw new IllegalArgumentException("Attempted to add duplicate value " + entry + " to config");
        }
    }

    /**
     * Get an entry in this config by name.
     *
     * @param name Name of entry.
     * @return Returns the key if found, null otherwise.
     */
    @Nullable
    public ConfigEntry<?> getEntryByName(String name) {
        return this.entries.getEntry(name);
    }

    /**
     * Attempts to deserialize a json element into a value for an entry,
     * then set that as the new value for that entry.
     *
     * @param entry Entry to set the new value of.
     * @param jsonElement JSON element to be deserialized.
     * @param <T> Type of object the entry stores.
     * @throws com.google.gson.JsonSyntaxException Thrown if the json element is not
     * a valid representation of T.
     * @throws IllegalArgumentException Thrown if <code>entry</code> is not a valid
     * entry in this config
     * @throws IllegalArgumentException Thrown if the value stored in <code>jsonElement</code>
     * is not valid for the config entry.
     */
    public <T> void deserializeAndSet(ConfigEntry<T> entry, JsonElement jsonElement) {
        T value = entry.deserialize(jsonElement);
        this.setValue(entry, value);
    }

    /**
     * Sets a new value for an entry, if this config contains that entry
     * and if the value is valid for that entry.
     *
     * @param entry Entry to set the new value of.
     * @param value Value to be set if valid.
     * @param <T> Type of object stored at the entry.
     * @param <V> Type of the value, must extend the type of the value stored at the entry.
     * @throws IllegalArgumentException Thrown if <code>entry</code> is not a valid
     * entry in this config
     * @throws IllegalArgumentException Thrown if <code>value</code> is not valid
     */
    public <T, V extends T> void setValue(ConfigEntry<T> entry, V value) {
        if (this.values.containsKey(entry)) {
            if (entry.isValid(value)) {
                this.values.put(entry, value);
            } else {
                throw new IllegalArgumentException("Invalid value of " + value + " for config entry " + entry.getName());
            }
        } else {
            StringBuilder msg = new StringBuilder("Attempted to add entry " + entry + " which does not exist in config!");
            msg.append(" Config has keys:\n");
            for (ConfigEntry<?> k : entries.getEntries()) {
                msg.append(" - ");
                msg.append(k.getName());
                msg.append("\n");
            }
            throw new IllegalArgumentException(msg.toString());
        }
    }

    /**
     * Updates this config with all entries from another config
     * that are part of this config.
     *
     * @param inConfig The config to copy into this config.
     */
    public void update(Config inConfig) {
        for (Map.Entry<ConfigEntry<?>, Object> entry : inConfig.values.entrySet()) {
            ConfigEntry<?> key = entry.getKey();
            if (this.values.containsKey(key)) {
                this.values.put(key, entry.getValue());
            }
        }
    }

    /**
     * Sets all entries in this config back to their default
     * values.
     */
    public void reset() {
        for (Map.Entry<ConfigEntry<?>, Object> entry : values.entrySet()) {
            ConfigEntry<?> key = entry.getKey();
            values.put(key, key.getDefaultValue());
        }
    }

    /**
     * Gets this configs file location
     *
     * @return Returns a JSON {@link File} that is the location
     * of this config.
     */
    public File getLocation() {
        return new File(parentDirectory.toFile(), identifier.getFileName());
    }

    /**
     * Gets the identity of the config
     *
     * @return Returns an {@link Identifier}
     */
    public Identifier getIdentifer() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config config = (Config) o;
        return identifier.equals(config.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @NotNull
    private final Path parentDirectory;
    @NotNull
    private final Identifier identifier;
    private final ConfigEntryRegistry entries = new ConfigEntryRegistry();
    private final Map<ConfigEntry<?>, Object> values = new HashMap<>();

}
