package com.github.thedeathlycow.simple.config.reload;

import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.entry.ConfigEntry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listener class for {@link ReloadEvent}s
 */
public class Reloadable {

    /**
     * Creates a reloadable listener for a config
     *
     * @param reloads {@link Config} that this listener reloads
     */
    public Reloadable(Config reloads) {
        this.reloads = reloads;
        LOGGER = Logger.getLogger("ConfigReloadable:" + reloads.getIdentifer().toString());
    }

    /**
     * Reloads the config from its file.
     * If an error is found, logs it and returns.
     */
    public void onReload() {
        reloads.reset();
        File configFile = reloads.getLocation();
        JsonObject json;
        try (FileReader reader = new FileReader(configFile)) {
            json = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reloading config=" + e.getMessage());
            return;
        }

        if (json != null) {
            updateConfig(json);
        }
    }

    /**
     * Takes a {@link JsonObject} from a config file and applies it to the
     * config. Any invalid fields in the json object are logged then skipped.
     *
     * @param json {@link JsonObject} from config file to apply to config.
     */
    private void updateConfig(JsonObject json) {
        Config configIn = Config.createTempConfig(reloads);
        for (Map.Entry<String, JsonElement> jsonEntry : json.entrySet()) {
            String jsonKey = jsonEntry.getKey();
            ConfigEntry<?> entry;
            try {
                entry = reloads.getEntryByName(jsonKey);
            } catch (IllegalArgumentException exception) {
                // ignore entries that are not valid config keys
                LOGGER.info("Could not load config option '" + jsonKey + "' with reason: " + exception.getMessage());
                continue;
            }

            if (entry != null) {
                configIn.addEntry(entry);
                configIn.deserializeAndSet(entry, jsonEntry.getValue());
            }
        }
        reloads.update(configIn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reloadable that = (Reloadable) o;
        return reloads.equals(that.reloads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reloads);
    }

    @NotNull
    private final Config reloads;
    private final Logger LOGGER;

}
