package com.github.thedeathlycow.simple.config.entry;

import org.jetbrains.annotations.NotNull;

/**
 * Config key for byte values.
 *
 * @author TheDeathlyCow
 */
public class ByteEntry extends BoundedEntry<Byte> {

    /**
     * Constructs a byte key with default bounds of
     * -2<sup>7</sup> (inclusive) and 2<sup>7</sup> - 1 (inclusive).
     * Effectively unbounded.
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     */
    public ByteEntry(@NotNull String name, @NotNull Byte defaultValue) {
        this(name, defaultValue, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    /**
     * Constructs a byte key with a given minimum and a
     * default maximum of 2<sup>7</sup> - 1 (inclusive).
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     * @param min          The given minimum value of this key.
     */
    public ByteEntry(@NotNull String name, @NotNull Byte defaultValue, Byte min) {
        this(name, defaultValue, min, Byte.MAX_VALUE);
    }

    /**
     * Constructs a byte key with a given minimum and maximum
     * bounds.
     *
     * @param name         Name of the key.
     * @param defaultValue Default value of the key.
     * @param min          The given minimum value of this key.
     * @param max          The given maximum value of this key.
     */
    public ByteEntry(@NotNull String name, @NotNull Byte defaultValue, Byte min, Byte max) {
        super(name, defaultValue, Byte.class, min, max);
    }
}
