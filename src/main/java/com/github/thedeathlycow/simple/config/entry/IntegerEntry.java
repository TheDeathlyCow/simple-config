package com.github.thedeathlycow.simple.config.entry;

import org.jetbrains.annotations.NotNull;

/**
 * Config key for integer values.
 *
 * @author TheDeathlyCow
 */
public class IntegerEntry extends BoundedEntry<Integer> {

    /**
     * Constructs an integer key with default bounds of
     * -2<sup>31</sup> (inclusive) and 2<sup>31</sup> - 1 (inclusive).
     * Effectively unbounded.
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     */
    public IntegerEntry(@NotNull String name, @NotNull Integer defaultValue) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Constructs an integer key with a given minimum and a
     * default maximum of 2<sup>31</sup> - 1 (inclusive).
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     * @param min          The given minimum value of this key.
     */
    public IntegerEntry(@NotNull String name, @NotNull Integer defaultValue, Integer min) {
        this(name, defaultValue, min, Integer.MAX_VALUE);
    }

    /**
     * Constructs an integer key with a given minimum and maximum
     * bounds.
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     * @param min          The given minimum value of this key.
     * @param max          The given maximum value of this key.
     */
    public IntegerEntry(@NotNull String name, @NotNull Integer defaultValue, Integer min, Integer max) {
        super(name, defaultValue, Integer.class, min, max);
    }
}
