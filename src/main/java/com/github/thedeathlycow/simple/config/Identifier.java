package com.github.thedeathlycow.simple.config;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

public record Identifier(@NotNull String namespace, @NotNull String name) {

    public Identifier(@NotNull String namespace, @NotNull String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public String getFileName() {
        return String.valueOf(Path.of(namespace, name + ".json"));
    }

    @Override
    public String toString() {
        return String.format("%s:%s", namespace, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier identifier = (Identifier) o;
        return namespace.equals(identifier.namespace) && name.equals(identifier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, name);
    }

}
