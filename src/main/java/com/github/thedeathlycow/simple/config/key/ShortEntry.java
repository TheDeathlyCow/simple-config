package com.github.thedeathlycow.simple.config.key;

import org.jetbrains.annotations.NotNull;

/**
 * Config key for short values.
 *
 * @author TheDeathlyCow
 */
public class ShortEntry extends BoundedEntry<Short> {

    /**
     * Constructs a short key with default bounds of
     * -2<sup>15</sup> (inclusive) and 2<sup>15</sup> - 1 (inclusive).
     * Effectively unbounded.
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     */
    public ShortEntry(@NotNull String name, @NotNull Short defaultValue) {
        this(name, defaultValue, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    /**
     * Constructs a short key with a given minimum and a
     * default maximum of 2<sup>15</sup> - 1 (inclusive).
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     * @param min          The given minimum value of this key.
     */
    public ShortEntry(@NotNull String name, @NotNull Short defaultValue, Short min) {
        this(name, defaultValue, min, Short.MAX_VALUE);
    }

    /**
     * Constructs a short key with a given minimum and maximum
     * bounds.
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     * @param min          The given minimum value of this key.
     * @param max          The given maximum value of this key.
     */
    public ShortEntry(@NotNull String name, @NotNull Short defaultValue, Short min, Short max) {
        super(name, defaultValue, Short.class, min, max);
    }
}
