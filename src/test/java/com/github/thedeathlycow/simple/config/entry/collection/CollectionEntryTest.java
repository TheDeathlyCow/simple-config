package com.github.thedeathlycow.simple.config.entry.collection;

import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionEntryTest {

    private List<Integer> ints;
    private ListEntry<Integer> integerCollectionEntry;
    private Config config;

    @BeforeEach
    public void setup() {
        this.ints = List.of(1, 2, 3, 4);
        this.integerCollectionEntry = new ListEntry<>("ints", ints, Integer.class, ArrayList::new);
        this.config = ConfigFactory.createConfigWithKeys(
                "test", "temp", Paths.get("."),
                integerCollectionEntry
        );
    }

    @Test
    public void castedListDoesNotThrow() {
        assertDoesNotThrow(() -> {
            List<Integer> ints = this.config.get(integerCollectionEntry);
        });
    }

    @Test
    public void returnedListIsCorrectValue() {
        assertEquals(this.ints, this.config.get(integerCollectionEntry));
    }

    @Test
    public void listWithMoreValuesNotEqual() {
        List<Integer> randomInts = new ArrayList<>(this.ints);
        randomInts.add(5);
        assertNotEquals(randomInts, this.config.get(integerCollectionEntry));
    }

    @Test
    public void listWithDiffernetValuesNotEqual() {
        List<Integer> randomInts = new ArrayList<>(this.ints);
        randomInts.set(0, -1);
        assertNotEquals(randomInts, this.config.get(integerCollectionEntry));
    }
}