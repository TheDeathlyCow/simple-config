package com.github.thedeathlycow.simple.config.key;

import org.jetbrains.annotations.NotNull;

/**
 * Config key for double values.
 *
 * @author TheDeathlyCow
 */
public class DoubleEntry extends BoundedEntry<Double> {

    /**
     * Constructs a double key with default bounds of
     * negative infinity and positive infinity.
     * Effectively unbounded.
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     */
    public DoubleEntry(@NotNull String name, @NotNull Double defaultValue) {
        this(name, defaultValue, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * Constructs a short key with a given minimum and a
     * default maximum of infinity.
     *
     * @param name Name of the key.
     * @param defaultValue Default value of the key.
     * @param min The given minimum value of this key.
     */
    public DoubleEntry(@NotNull String name, @NotNull Double defaultValue, Double min) {
        this(name, defaultValue, min, Double.POSITIVE_INFINITY);
    }
    /**
     * Constructs a double key with a given minimum and maximum
     * bounds.
     *
     * @param name Name of the key.
     * @param defaultValue Default value of the key.
     * @param min The given minimum value of this key.
     * @param max The given maximum value of this key.
     */
    public DoubleEntry(@NotNull String name, @NotNull Double defaultValue, Double min, Double max) {
        super(name, defaultValue, Double.class, min, max);
    }

}
