package com.github.thedeathlycow.simple.config.entry;

import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListEntryTest {

    private List<Integer> ints;
    private ListEntry<Integer> integerListEntry;
    private Config config;

    @BeforeEach
    public void setup() {
        this.ints = List.of(1, 2, 3, 4);
        this.integerListEntry = new ListEntry<>("ints", ints, Integer.class);
        this.config = ConfigFactory.createConfigWithKeys(
                "test", "temp", Paths.get("."),
                integerListEntry
        );
    }

    @Test
    public void castedListDoesNotThrow() {
        assertDoesNotThrow(() -> {
            List<Integer> ints = this.config.get(integerListEntry);
        });
    }

    @Test
    public void returnedListIsCorrectValue() {
        assertEquals(this.ints, this.config.get(integerListEntry));
    }

    @Test
    public void listWithMoreValuesNotEqual() {
        List<Integer> randomInts = new ArrayList<>(this.ints);
        randomInts.add(5);
        assertNotEquals(randomInts, this.config.get(integerListEntry));
    }

    @Test
    public void listWithDiffernetValuesNotEqual() {
        List<Integer> randomInts = new ArrayList<>(this.ints);
        randomInts.set(0, -1);
        assertNotEquals(randomInts, this.config.get(integerListEntry));
    }
}