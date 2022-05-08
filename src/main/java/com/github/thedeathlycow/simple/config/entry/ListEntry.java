package com.github.thedeathlycow.simple.config.entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//@SuppressWarnings({"unchecked"})
public class ListEntry<T> extends ConfigEntry<List<T>> {

    /**
     * Constructs a config entry with a name, default value, type, and deserializer.
     * Sets the array creator to be ArrayList::new.
     *
     * @param name                Name of the config entry.
     * @param defaultValue        Default value of the entry in a config.
     * @param type                The type of the elements of the list. May not be null.
     * @param elementDeserializer JSON deserializer for elements
     */
    public ListEntry(@NotNull String name, @NotNull List<T> defaultValue, @NotNull Class<T> type, @NotNull JsonDeserializer<T> elementDeserializer) {
        this(name, defaultValue, type, elementDeserializer, ArrayList::new);
    }

    /**
     * Constructs a list entry with a name, default value, type, new list creator, and deserializer.
     *
     * @param name                Name of the list entry.
     * @param defaultValue        Default value of the entry in a config.
     * @param type                The type of the elements of the list. May not be null.
     * @param elementDeserializer JSON deserializer for elements
     * @param newListCreator      Factory for creating new list
     */
    public ListEntry(@NotNull String name, @NotNull List<T> defaultValue, @NotNull Class<T> type, @NotNull JsonDeserializer<T> elementDeserializer, @NotNull NewListCreator<T> newListCreator) {
        super(name, defaultValue, null);
        this.collectionType = type;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(collectionType, elementDeserializer)
                .create();
        this.newListCreator = newListCreator;
    }

    @Override
    public List<T> adapt(Object obj) {

        List<T> castedItems = this.newListCreator.create();
        if (obj instanceof List list) {
            for (Object element : list) {
                castedItems.add(this.collectionType.cast(element));
            }
        }

        return this.getDefaultValue().stream()
                .map((element) -> {
                    int i = this.getDefaultValue().indexOf(element);
                    return castedItems.get(i);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<T> deserialize(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            return deserializeIterable(jsonElement.getAsJsonArray());
        } else {
            return deserializeIterable(Collections.singleton(jsonElement));
        }
    }

    private List<T> deserializeIterable(Iterable<JsonElement> iterable) {
        List<T> newList = this.newListCreator.create();
        for (JsonElement elem : iterable) {
            newList.add(this.gson.fromJson(elem, collectionType));
        }
        return newList;
    }

    @Override
    public boolean isValid(List<T> value) {
        return true;
    }

    public interface NewListCreator<T> {
        List<T> create();
    }

    private final Class<T> collectionType;
    private final Gson gson;
    private final NewListCreator<T> newListCreator;
}
