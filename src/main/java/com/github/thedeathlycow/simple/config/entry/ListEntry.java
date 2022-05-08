package com.github.thedeathlycow.simple.config.entry;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A config entry for lists. Handles adaption
 * and deserialization for lists.
 *
 * @param <T> The type of the objects stored in the list.
 */
public class ListEntry<T> extends ConfigEntry<List<T>> {

    /**
     * Constructs a config entry with a name, default value, type, and deserializer.
     * Sets the array creator to be ArrayList::new.
     *
     * @param name         Name of the config entry.
     * @param defaultValue Default value of the entry in a config.
     * @param type         The type of the elements of the list. May not be null.
     */
    public ListEntry(@NotNull String name, @NotNull List<T> defaultValue, @NotNull Class<T> type) {
        this(name, defaultValue, type, ArrayList::new);
    }

    /**
     * Constructs a list entry with a name, default value, type, new list creator, and deserializer.
     *
     * @param name           Name of the list entry.
     * @param defaultValue   Default value of the entry in a config.
     * @param type           The type of the elements of the list. May not be null.
     * @param newListCreator Factory for creating new list
     */
    public ListEntry(@NotNull String name, @NotNull List<T> defaultValue, @NotNull Class<T> type, @NotNull NewListCreator<T> newListCreator) {
        super(name, defaultValue, null);
        this.collectionType = type;
        this.newListCreator = newListCreator;
    }

    /**
     * Adapts an object into an instance {@link List<T>} of T.
     *
     * @param obj Object to adapt
     * @return Returns <code>obj</code> as a reference to a {@link List<T>} of T.
     * @throws ClassCastException Thrown if object is not a valid instance of {@link List<T>}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> adapt(Object obj) {
        return (List<T>) obj;
    }

    /**
     * Deserializes a JSON element into a list of T. If
     * the JSON element is not an array, will treat it as a singleton
     * list.
     *
     * @param jsonElement JSON element to deserialize
     * @return Returns the {@link List<T>} of T that represents the given JSON element.
     * @throws com.google.gson.JsonSyntaxException Thrown if the
     *                                             json element is not a valid representation of T.
     */
    @Override
    public List<T> deserialize(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            return deserializeIterable(jsonElement.getAsJsonArray());
        } else {
            return deserializeIterable(Collections.singleton(jsonElement));
        }
    }

    /**
     * Deserializes a JSON element that is an element
     * of the list into an object of type T. For non-standard
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
     * List entries do not have any validation checks by default.
     *
     * @param value Value to check.
     * @return Returns true.
     */
    @Override
    public boolean isValid(List<T> value) {
        return true;
    }

    /**
     * Interface for defining how to create a new list. As {@link List} is an
     * abstract class, it is unknown how a user might want to
     *
     * @param <T>
     */
    public interface NewListCreator<T> {
        List<T> create();
    }

    /**
     * Deserializes an iterable of JSON elements into a {@link List} of T.
     *
     * @param iterable Iterable of JSON elements to deserialize
     * @return Returns the {@link List} of T that <code>iterable</code> represents.
     */
    private List<T> deserializeIterable(Iterable<JsonElement> iterable) {
        List<T> newList = this.newListCreator.create();
        for (JsonElement elem : iterable) {
            newList.add(deserializeElement(elem));
        }
        return newList;
    }

    private final Class<T> collectionType;
    private final NewListCreator<T> newListCreator;
}
