package com.github.thedeathlycow.simple.config.entry;

import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BoundedEntryTest {
    private IntegerEntry unBoundedEntry;
    private IntegerEntry minBoundedEntry;
    private IntegerEntry boundedEntry;
    private Config config;

    @BeforeEach
    void setupConfig() {
        this.unBoundedEntry = new IntegerEntry("unbounded", 1);
        this.minBoundedEntry = new IntegerEntry("minBounded", 1, 0);
        this.boundedEntry = new IntegerEntry("bounded", 1, 0, 10);
        this.config = ConfigFactory.createConfigWithKeys(
                "test", "temp", Paths.get("."),
                unBoundedEntry,
                minBoundedEntry,
                boundedEntry
        );
    }

    //* Unbounded entry tests *//

    @Test
    void unboundedEntrySetZeroDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(unBoundedEntry, 0));
    }

    @Test
    void unboundedEntrySetMinIntDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(unBoundedEntry, Integer.MIN_VALUE));
    }

    @Test
    void unboundedEntrySetMaxIntDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(unBoundedEntry, Integer.MAX_VALUE));
    }

    //* Min-bounded entry tests *//

    @Test
    void minBoundedEntrySetZeroDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(minBoundedEntry, 0));
    }

    @Test
    void minBoundedEntrySetMaxIntDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(minBoundedEntry, Integer.MAX_VALUE));
    }

    @Test
    void minBoundedEntrySetMinIntDoesThrow() {
        assertThrows(IllegalArgumentException.class, () -> this.config.setValue(minBoundedEntry, Integer.MIN_VALUE));
    }

    @Test
    void minBoundedEntrySetMinBoundDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(minBoundedEntry, minBoundedEntry.getMin()));
    }

    //* Fully-bounded entry tests *//

    @Test
    void boundedEntrySetZeroDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(boundedEntry, 0));
    }

    @Test
    void boundedEntrySetMinIntDoesThrow() {
        assertThrows(IllegalArgumentException.class, () -> this.config.setValue(boundedEntry, Integer.MIN_VALUE));
    }

    @Test
    void boundedEntrySetMaxIntDoesThrow() {
        assertThrows(IllegalArgumentException.class, () -> this.config.setValue(boundedEntry, Integer.MAX_VALUE));
    }

    @Test
    void boundedEntrySetMinBoundDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(boundedEntry, boundedEntry.getMin()));
    }

    @Test
    void boundedEntrySetMaxBoundDoesNotThrow() {
        assertDoesNotThrow(() -> this.config.setValue(boundedEntry, boundedEntry.getMax()));
    }

}