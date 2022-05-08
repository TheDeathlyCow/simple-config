package com.github.thedeathlycow.simple.config.entry.collection;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class SetEntry<T> extends CollectionEntry<T, Set<T>> {

    /**
     * Constructs a set entry with a name, default value, and type.
     * Uses a hash set as the default creator.
     *
     * @param name         Name of the set entry.
     * @param defaultValue Default value of the entry in a config.
     * @param type         The type of the elements of the set. May not be null.
     */
    public SetEntry(@NotNull String name, @NotNull Set<T> defaultValue, @NotNull Class<T> type) {
        this(name, defaultValue, type, HashSet::new);
    }

    /**
     * Constructs a set entry with a name, default value, type, and a new set creator.
     *
     * @param name              Name of the collection entry.
     * @param defaultValue      Default value of the entry in a config.
     * @param type              The type of the elements of the collection. May not be null.
     * @param collectionCreator Factory for creating new set
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public SetEntry(@NotNull String name, @NotNull Set<T> defaultValue, @NotNull Class<T> type, @NotNull CollectionCreator<T, Set<T>> collectionCreator) {
        super(name, defaultValue, type, (Class) Set.class, collectionCreator);
    }
}
