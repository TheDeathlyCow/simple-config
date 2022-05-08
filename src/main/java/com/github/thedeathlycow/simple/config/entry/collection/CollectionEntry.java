package com.github.thedeathlycow.simple.config.entry.collection;

import com.github.thedeathlycow.simple.config.entry.ConfigEntry;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * A config entry for collections. Handles adaption
 * and deserialization for collections.
 *
 * @param <T> The type of the objects stored in the collection.
 * @param <C> The type of collection of T that this entry uses.
 */
public class CollectionEntry<T, C extends Collection<T>> extends ConfigEntry<C> {

    /**
     * Constructs a collection entry with a name, default value, type, and a new collection creator.
     *
     * @param name              Name of the collection entry.
     * @param defaultValue      Default value of the entry in a config.
     * @param type              The type of the elements of the collection. May not be null.
     * @param collectionCreator Factory for creating new collection
     */
    public CollectionEntry(@NotNull String name, @NotNull C defaultValue, @NotNull Class<T> type, @NotNull Class<C> collectionType, @NotNull CollectionCreator<T, C> collectionCreator) {
        super(name, defaultValue, collectionType);
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
    public C deserialize(JsonElement jsonElement) {
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
    public boolean isValid(C value) {
        return true;
    }

    /**
     * Interface for defining how to create a new collection. As {@link Collection} is an
     * abstract class, it is unknown how an implementation might want to create
     * the new collection.
     *
     * @param <T>
     */
    public interface CollectionCreator<T, C extends Collection<T>> {
        C create();
    }

    /**
     * Deserializes an iterable of JSON elements into a {@link Collection} of T.
     *
     * @param iterable Iterable of JSON elements to deserialize
     * @return Returns the {@link Collection} of T that <code>iterable</code> represents.
     */
    private C deserializeIterable(Iterable<JsonElement> iterable) {
        C deserialized = this.collectionCreator.create();
        for (JsonElement elem : iterable) {
            deserialized.add(deserializeElement(elem));
        }
        return deserialized;
    }

    private final Class<T> collectionType;
    private final CollectionCreator<T, C> collectionCreator;
}
