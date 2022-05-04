package com.github.thedeathlycow.simple.config.key;

import org.jetbrains.annotations.NotNull;

/**
 * Config key for boolean values.
 *
 * @author TheDeathlyCow
 */
public class BooleanEntry extends ConfigEntry<Boolean> {

    public BooleanEntry(@NotNull String name, @NotNull Boolean defaultValue) {
        super(name, defaultValue, Boolean.class);
    }

    /**
     * Boolean keys are always valid
     *
     * @param value Value to check.
     * @return Returns true.
     */
    @Override
    public boolean isValid(Boolean value) {
        return true;
    }
}
