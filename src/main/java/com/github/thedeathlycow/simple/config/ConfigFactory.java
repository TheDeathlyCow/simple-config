package com.github.thedeathlycow.simple.config;

import com.github.thedeathlycow.simple.config.key.ConfigEntry;

import java.io.File;
import java.nio.file.Path;

/**
 * Provides methods for creating configs.
 *
 * @author TheDeathlyCow
 */
public class ConfigFactory {

    /**
     * Creates a datapack reloadable config with no keys.
     * Directory and file type must be supplied.
     *
     * @param namespace ID / Namespace of the mod creating the config.
     * @param configName The name of the config.
     * @param parentDir The parent directory of the config
     * @return Returns the new config
     */
    public static Config createEmptyConfig(String namespace, String configName, Path parentDir) {
        return createConfigWithKeys(namespace, configName, parentDir);
    }

    /**
     * Creates a config with several keys already given.
     * Directory determined from identifier
     *
     * @param namespace Namespace of the artefact creating the config.
     * @param configName The name of the config.
     * @param parentDir The parent directory of the config
     * @param entries The starting keys of this config.
     * @return Returns the new config
     */
    public static Config createConfigWithKeys(String namespace, String configName, Path parentDir, ConfigEntry<?>... entries) {
        Config config = new Config(new Identifier(namespace, configName), parentDir);

        for (ConfigEntry<?> entry : entries) {
            config.addEntry(entry);
        }

        return config;
    }
}
