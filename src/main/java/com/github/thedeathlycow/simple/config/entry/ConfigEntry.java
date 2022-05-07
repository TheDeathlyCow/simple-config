package com.github.thedeathlycow.simple.config.entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A config key is an entry in a config. It has a name, type, and default
 * value. The default value will be the value set for this key when it is
 * added to a config, but is not necessarily always going to be the value
 * for this key in that config.
 * <p>
 * This class provides methods for deserializing JSON objects into the type
 * that the key stores as well as for determining the validity of the type.
 * Validity must be implemented by concrete subclasses.
 * <p>
 * Important: equals() and hashCode() for this object is based solely on
 * its name, as it is meant to function like an in-memory JSON key.
 *
 * @param <T> The type that this key stores in a config.
 * @author TheDeathlyCow
 */
public abstract class ConfigEntry<T> {

    /**
     * Constructs a config key with a name, default value, and type.
     *
     * @param name         Name of the config key.
     * @param defaultValue Default value of the key in a config.
     * @param type         The type of the value.
     */
    public ConfigEntry(@NotNull String name, @NotNull T defaultValue, @NotNull Class<T> type) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public T getDefaultValue() {
        return defaultValue;
    }

    @NotNull
    public Class<T> getType() {
        return type;
    }

    /**
     * Deserializes a json element into this key's type.
     * Does NOT check for validity.
     *
     * @param jsonElement JSON element to deserialize
     * @return Returns the deserialized object.
     * @throws com.google.gson.JsonSyntaxException Thrown if the
     *                                             json element is not a valid representation of T.
     */
    public T deserialize(JsonElement jsonElement) {
        return GSON.fromJson(jsonElement, getType());
    }

    /**
     * Determines if the value is valid for this key.
     *
     * @param value Value to check.
     * @return Returns a boolean representing whether value is valid.
     */
    public abstract boolean isValid(T value);

    /**
     * Two config keys are equal if and only if their names match.
     *
     * @param o Other config key.
     * @return Returns a boolean representing if the two keys are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigEntry<?> configEntry = (ConfigEntry<?>) o;
        return name.equals(configEntry.name);
    }

    /**
     * Hashes a config key using the config key's name.
     *
     * @return Returns the hash code of this config key.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ConfigKey{" +
                "name='" + name + '\'' +
                ", defaultValue=" + defaultValue +
                ", type=" + type +
                '}';
    }

    @NotNull
    private final String name;

    @NotNull
    private final T defaultValue;

    @NotNull
    private final Class<T> type;

    private static final Gson GSON = new GsonBuilder()
            .create();

}
