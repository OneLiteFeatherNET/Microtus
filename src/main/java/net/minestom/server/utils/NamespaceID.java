package net.minestom.server.utils;

import net.kyori.adventure.key.Key;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a namespaced ID
 * https://minecraft.wiki/w/Namespaced_ID
 * @deprecated Use {@link Key} instead!
 */
@Deprecated
public record NamespaceID(@NotNull String domain, @NotNull String path) implements CharSequence, Key {
    private static final java.util.regex.Pattern LEGAL_LETTERS = java.util.regex.Pattern.compile("[a-z0-9_-]+");
    private static final java.util.regex.Pattern LEGAL_PATH_LETTERS = java.util.regex.Pattern.compile("[0-9a-z./_-]+");

    /**
     * Creates a new {@link NamespaceID} from the given namespace
     * @param namespace the namespace
     * @return a new {@link NamespaceID}
     * @deprecated Use {@link Key#key(String)} instead!
     * @throws IllegalArgumentException if the domain or path contains illegal characters
     */
    @Contract("_ -> new")
    public static @NotNull NamespaceID from(@NotNull String namespace) {
        final int index = namespace.indexOf(Key.DEFAULT_SEPARATOR);
        final String domain;
        final String path;
        if (index == -1) {
            domain = Key.MINECRAFT_NAMESPACE;
            path = namespace;
        } else {
            domain = namespace.substring(0, index);
            path = namespace.substring(index + 1);
        }
        return new NamespaceID(domain, path);
    }

    /**
     * Creates a new {@link NamespaceID} from the given domain and path
     * @param domain the domain
     * @param path the path
     * @return a new {@link NamespaceID}
     * @deprecated Use {@link Key#key(String, String)} instead!
     * @throws IllegalArgumentException if the domain or path contains illegal characters
     */
    @Contract("_, _ -> new")
    public static @NotNull NamespaceID from(@NotNull String domain, @NotNull String path) {
        return new NamespaceID(domain, path);
    }

    /**
     * Creates a new {@link NamespaceID} from the given key
     * @param key the key
     * @return a new {@link NamespaceID}
     * @deprecated Use {@link Key#namespace()} and {@link Key#value()} instead!
     * @throws IllegalArgumentException if the domain or path contains illegal characters
     */
    @Deprecated
    @Contract("_ -> new")
    public static @NotNull NamespaceID from(@NotNull Key key) {
        return new NamespaceID(key.namespace(), key.value());
    }

    public NamespaceID {
        if (!LEGAL_LETTERS.matcher(domain).matches()) {
            throw new IllegalArgumentException("Illegal character in domain. Must match " + LEGAL_LETTERS);
        }
        if (!LEGAL_PATH_LETTERS.matcher(path).matches()) {
            throw new IllegalArgumentException("Illegal character in path. Must match " + LEGAL_PATH_LETTERS);
        }
    }

    @Override
    public int length() {
        return domain.length() + 1 + path.length();
    }

    @Override
    public char charAt(int index) {
        if (index < domain.length()) {
            return domain.charAt(index);
        } else if (index == domain.length()) {
            return ':';
        } else {
            return path.charAt(index - domain.length() - 1);
        }
    }

    @Override
    public @NotNull CharSequence subSequence(int start, int end) {
        return asString().subSequence(start, end);
    }

    @Override
    @Pattern("[a-z0-9_\\-.]+")
    public @NotNull String namespace() {
        return this.domain;
    }

    @Override
    public @NotNull String value() {
        return this.path;
    }

    @Override
    public @NotNull String asString() {
        return domain + ':' + path;
    }

    @Override
    public @NotNull String toString() {
        return asString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final Key that)) return false;
        return Objects.equals(this.domain, that.namespace()) && Objects.equals(this.path, that.value());
    }

    @Override
    public int hashCode() {
        int result = this.domain.hashCode();
        result = (31 * result) + this.path.hashCode();
        return result;
    }
}
