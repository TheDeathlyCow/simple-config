package com.github.thedeathlycow.simple.config.key;

import org.jetbrains.annotations.NotNull;

/**
 * A config key that stores a comparable object and whose validity is based
 * on whether or not the value is in within the bound of its minimum (inclusive)
 * and maximum (inclusive) value.
 *
 * @param <T> Comparable type this key stores.
 * @author TheDeathlyCow
 */
public class BoundedEntry<T extends Comparable<T>> extends ConfigEntry<T> {

    /**
     * Constructs a bounded key with a name, default value, type,
     * and bounds.
     *
     * @param name         Name of the bounded key.
     * @param defaultValue Default value of the bounded key.
     * @param type         Type of the object stored at this key.
     * @param min          Minimum allowed value for this key (inclusive).
     * @param max          Maximum allowed value for this key (inclusive).
     * @throws IllegalArgumentException Thrown if the default value is
     *                                  not within the bounds of min and max.
     */
    public BoundedEntry(@NotNull String name, @NotNull T defaultValue, Class<T> type, T min, T max) {
        super(name, defaultValue, type);
        this.min = min;
        this.max = max;

        if (!isValid(defaultValue)) {
            throw new IllegalArgumentException("Default value for bounded key " + name + " is invalid");
        }
    }

    /**
     * Determines if the value given is greater than or equal to the
     * minimum value and less than or equal to the maximum value.
     *
     * @param value Value to check.
     * @return Returns a boolean determining if the given value is
     * within the bounds of this key.
     */
    @Override
    public boolean isValid(T value) {
        return min.compareTo(value) <= 0 && max.compareTo(value) >= 0;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    private final T min;
    private final T max;
}
