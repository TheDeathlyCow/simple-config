package com.github.thedeathlycow.simple.config.entry.collection;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListEntry<T> extends CollectionEntry<T, List<T>> {

    /**
     * Constructs a list entry with a name, default value, and type.
     * Uses an array list as the default creator.
     *
     * @param name         Name of the list entry.
     * @param defaultValue Default value of the entry in a config.
     * @param type         The type of the elements of the collection. May not be null.
     */
    public ListEntry(@NotNull String name, @NotNull List<T> defaultValue, @NotNull Class<T> type) {
        this(name, defaultValue, type, ArrayList::new);
    }

    /**
     * Constructs a list entry with a name, default value, type, and a new collection creator.
     *
     * @param name              Name of the list entry.
     * @param defaultValue      Default value of the entry in a config.
     * @param type              The type of the elements of the list. May not be null.
     * @param collectionCreator Factory for creating new collection
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ListEntry(@NotNull String name, @NotNull List<T> defaultValue, @NotNull Class<T> type, @NotNull CollectionCreator<T, List<T>> collectionCreator) {
        super(name, defaultValue, type, (Class) List.class, collectionCreator);
    }
}
