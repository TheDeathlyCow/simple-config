package com.github.thedeathlycow.simple.config.entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Collection;

@SuppressWarnings({"unchecked"})
public class CollectionEntry<T> extends ConfigEntry<Collection<T>> {

    /**
     * Constructs a config entry with a name, default value, and type.
     *
     * @param name         Name of the config entry.
     * @param defaultValue Default value of the entry in a config.
     * @param type         The type of the object stored in this collection.
     * @param deserializer JSON deserializer for the elements of this collection
     */
    public CollectionEntry(@NotNull String name, @NotNull Collection<T> defaultValue, @NotNull Class<T> type, JsonDeserializer<T> deserializer) {
        super(name, defaultValue, (Class<Collection<T>>) (Class<?>) Collection.class);
        this.collectionType = new TypeToken<Collection<T>>() {
        }.getType();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(type, deserializer)
                .create();
    }

    @Override
    public Collection<T> deserialize(JsonElement element) {
        Collection<T> deserialized = null;
        if (element.isJsonArray()) {
            deserialized = gson.fromJson(element, collectionType);
        }
        return deserialized;
    }

    /**
     * Collection keys have no restrictions by default.
     *
     * @param value Value to check.
     * @return Returns true
     */
    @Override
    public boolean isValid(Collection<T> value) {
        return false;
    }

    private final Type collectionType;
    private final Gson gson;
}
