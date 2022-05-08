package com.github.thedeathlycow.simple.config.entry;

import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

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
}