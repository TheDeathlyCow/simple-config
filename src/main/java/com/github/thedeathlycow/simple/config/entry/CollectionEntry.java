package com.github.thedeathlycow.simple.config.entry;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A config entry for collections. Handles adaption
 * and deserialization for collections.
 *
 * @param <T> The type of the objects stored in the collection.
 */
public class CollectionEntry<T> extends ConfigEntry<Collection<T>> {

    /**
     * Constructs a config entry with a name, default value, type, and deserializer.
     * Sets the collection creator to be ArrayList::new.
     *
     * @param name         Name of the config entry.
     * @param defaultValue Default value of the entry in a config.
     * @param type         The type of the elements of the collection. May not be null.
     */
    public CollectionEntry(@NotNull String name, @NotNull Collection<T> defaultValue, @NotNull Class<T> type) {
        this(name, defaultValue, type, ArrayList::new);
    }

    /**
     * Constructs a collection entry with a name, default value, type, new collection creator, and deserializer.
     *
     * @param name              Name of the collection entry.
     * @param defaultValue      Default value of the entry in a config.
     * @param type              The type of the elements of the collection. May not be null.
     * @param collectionCreator Factory for creating new collection
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public CollectionEntry(@NotNull String name, @NotNull Collection<T> defaultValue, @NotNull Class<T> type, @NotNull CollectionEntry.CollectionCreator<T> collectionCreator) {
        super(name, defaultValue, (Class) Collection.class);
        this.collectionType = type;
        this.collectionCreator = collectionCreator;
    }

    /**
     * Deserializes a JSON element into a collection of T. If
     * the JSON element is not an array, will treat it as a singleton
     * collection.
     *
     * @param jsonElement JSON element to deserialize
     * @return Returns the {@link Collection} of T that represents the given JSON element.
     * @throws com.google.gson.JsonSyntaxException Thrown if the
     *                                             json element is not a valid representation of T.
     */
    @Override
    public Collection<T> deserialize(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            return deserializeIterable(jsonElement.getAsJsonArray());
        } else {
            return deserializeIterable(Collections.singleton(jsonElement));
        }
    }

    /**
     * Deserializes a JSON element that is an element
     * of the collection into an object of type T. For non-standard
     * object types, this method should be overridden by subclasses.
     *
     * @param element JSON element to deserialize
     * @return Returns the object that <code>element</code> represents as an
     * instance of T.
     * @throws com.google.gson.JsonSyntaxException Thrown if the
     *                                             json element is not a valid representation of T.
     */
    protected T deserializeElement(JsonElement element) {
        return GSON.fromJson(element, collectionType);
    }

    /**
     * Collection entries do not have any validation checks by default.
     *
     * @param value Value to check.
     * @return Returns true.
     */
    @Override
    public boolean isValid(Collection<T> value) {
        return true;
    }

    /**
     * Interface for defining how to create a new collection. As {@link Collection} is an
     * abstract class, it is unknown how an implementation might want to create
     * the new collection.
     *
     * @param <T>
     */
    public interface CollectionCreator<T> {
        Collection<T> create();
    }

    /**
     * Deserializes an iterable of JSON elements into a {@link Collection} of T.
     *
     * @param iterable Iterable of JSON elements to deserialize
     * @return Returns the {@link Collection} of T that <code>iterable</code> represents.
     */
    private Collection<T> deserializeIterable(Iterable<JsonElement> iterable) {
        Collection<T> deserialized = this.collectionCreator.create();
        for (JsonElement elem : iterable) {
            deserialized.add(deserializeElement(elem));
        }
        return deserialized;
    }

    private final Class<T> collectionType;
    private final CollectionCreator<T> collectionCreator;
}
