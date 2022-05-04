package com.github.thedeathlycow.simple.config;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class IdentifierTest {

    @Test
    public void identifierStringFitsCorrectFormat() {
        String namespace = "namespace";
        String name = "name";
        Identifier identifier = new Identifier(namespace, name);

        assertEquals(identifier.toString(), "namespace:name");
    }

    @Test
    public void canCreateFileWithoutSubdirs() {
        String namespace = "namespace";
        String name = "name";
        Identifier identifier = new Identifier(namespace, name);
        assertDoesNotThrow(() -> {
            String s = identifier.getFileName();
        });
    }

    @Test
    public void canCreateFileWithSubdirs() {
        String namespace = "namespace";
        String name = "dir/name";
        Identifier identifier = new Identifier(namespace, name);
        assertDoesNotThrow(() -> {
            String s = identifier.getFileName();
        });
    }

    @Test
    public void createdFileIsJSON() {
        String namespace = "namespace";
        String name = "name";
        Identifier identifier = new Identifier(namespace, name);
        String s = identifier.getFileName();
        assertTrue(s.endsWith(".json"));
    }

}