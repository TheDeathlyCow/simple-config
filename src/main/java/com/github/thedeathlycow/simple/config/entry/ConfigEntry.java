package com.github.thedeathlycow.simple.config.entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A config entry is a key:pair relationship in a config. It has a name, type, and default
 * value. The default value will be the value set for this entry when it is
 * added to a config, but is not necessarily always going to be the value
 * for this entry in that config.
 * <p>
 * This class provides methods for deserializing JSON objects into the type
 * that the entry stores as well as for determining the validity of the type.
 * Validity must be implemented by concrete subclasses.
 * <p>
 * Important: equals() and hashCode() for this object is based solely on
 * its name, as it is meant to function like an in-memory JSON key.
 *
 * @param <T> The type that this entry stores in a config.
 * @author TheDeathlyCow
 */
public abstract class ConfigEntry<T> {

    /**
     * Constructs a config entry with a name, default value, and type.
     *
     * @param name         Name of the config entry.
     * @param defaultValue Default value of the entry in a config.
     * @param type         The type of the value. May be null.
     */
    public ConfigEntry(@NotNull String name, @NotNull T defaultValue, @Nullable Class<T> type) {
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

    /**
     * @return Gets the class of the object stored in this entry.
     */
    @Nullable
    protected Class<T> getType() {
        return type;
    }

    /**
     * Adapts an object into an instance of T.
     *
     * @param obj Object to adapt
     * @return Returns object as an instance of T.
     * @throws IllegalStateException Thrown if type is null.
     * @throws ClassCastException Thrown if object is not an instance of T.
     */
    public T adapt(Object obj) {
        Class<T> type = this.getType();
        if (type == null) {
            throw new IllegalStateException("Adapt must be overridden if type is null!");
        }
        return type.cast(obj);
    }

    /**
     * Deserializes a json element into this entry's type.
     * Does NOT check for validity.
     *
     * @param jsonElement JSON element to deserialize
     * @return Returns the deserialized object.
     * @throws com.google.gson.JsonSyntaxException Thrown if the
     *                                             json element is not a valid representation of T.
     */
    public T deserialize(JsonElement jsonElement) {
        return GSON.fromJson(jsonElement, this.getType());
    }

    /**
     * Determines if the value is valid for this entry.
     *
     * @param value Value to check.
     * @return Returns a boolean representing whether value is valid.
     */
    public abstract boolean isValid(T value);

    /**
     * Two config entries are equal if and only if their names match.
     *
     * @param o Other config entry.
     * @return Returns a boolean representing if the two entries are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigEntry<?> configEntry = (ConfigEntry<?>) o;
        return name.equals(configEntry.name);
    }

    /**
     * Hashes a config entry using the config entry's name.
     *
     * @return Returns the hash code of this config entry.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ConfigEntry{" +
                "name='" + name + '\'' +
                ", defaultValue=" + defaultValue +
                ", type=" + type +
                '}';
    }

    @NotNull
    private final String name;

    @NotNull
    private final T defaultValue;

    @Nullable
    private final Class<T> type;

    private static final Gson GSON = new GsonBuilder()
            .create();

}
