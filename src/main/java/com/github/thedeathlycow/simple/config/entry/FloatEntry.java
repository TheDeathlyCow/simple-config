package com.github.thedeathlycow.simple.config.entry;

import org.jetbrains.annotations.NotNull;

/**
 * Config entry for double values.
 *
 * @author TheDeathlyCow
 */
public class FloatEntry extends BoundedEntry<Float> {

    /**
     * Constructs a float entry with default bounds of
     * negative infinity and positive infinity.
     * Effectively unbounded.
     *
     * @param name         Name of the entry.
     * @param defaultValue Default value of the entry.
     */
    public FloatEntry(@NotNull String name, @NotNull Float defaultValue) {
        this(name, defaultValue, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    /**
     * Constructs a float entry with a given minimum and a
     * default maximum of infinity.
     *
     * @param name         Name of the entry.
     * @param defaultValue Default value of the entry.
     * @param min          The given minimum value of this entry.
     */
    public FloatEntry(@NotNull String name, @NotNull Float defaultValue, Float min) {
        this(name, defaultValue, min, Float.POSITIVE_INFINITY);
    }

    /**
     * Constructs a float entry with a given minimum and maximum
     * bounds.
     *
     * @param name         Name of the entry.
     * @param defaultValue Default value of the entry.
     * @param min          The given minimum value of this entry.
     * @param max          The given maximum value of this entry.
     */
    public FloatEntry(@NotNull String name, @NotNull Float defaultValue, Float min, Float max) {
        super(name, defaultValue, Float.class, min, max);
    }

}
