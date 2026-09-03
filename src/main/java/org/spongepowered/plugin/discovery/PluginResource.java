/*
 * This file is part of plugin-spi, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.plugin.discovery;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Represents a resource provided by a {@link PluginResourceLocator locator}.
 */
@SuppressWarnings({"removal", "DeprecatedIsStillUsed"})
public interface PluginResource extends org.spongepowered.plugin.ResourceQueryable {

    /**
     * @deprecated Use {@link #paths()} instead
     * @return The path where this resource originates from
     */
    @Deprecated(forRemoval = true, since = "0.5.0")
    default Path path() {
        return this.paths().getFirst();
    }

    /**
     * @return The paths where this resource originates from
     */
    List<Path> paths();

    /**
     * Retrieve a {@link String property} of this resource by {@link String key}.
     * <p>
     * Consult the vendor for expected keys.
     *
     * @param key The key
     * @return The value or {@link Optional#empty()} if not found
     */
    Optional<String> property(final String key);

    /**
     * Resolves the location of a bundled resource, given a relative path.
     *
     * @param path The relative path
     * @return The resolved location, if available
     */
    Optional<URI> locate(final String path);

    /**
     * Opens an {@link InputStream} of the location of a bundled resource, given a relative path.
     *
     * @param path The relative path
     * @return The opened bundled resource, if available
     */
    default Optional<InputStream> open(final String path) {
        return this.locate(path).flatMap(url -> {
            try {
                return Optional.of(url.toURL().openStream());
            } catch (final IOException ignored) {
                return Optional.empty();
            }
        });
    }

    /**
     * @deprecated Use {@link #locate(String)}
     */
    @Deprecated(forRemoval = true, since = "0.5.2")
    @Override
    default Optional<URI> locateResource(final String path) {
        return this.locate(path);
    }

    /**
     * @deprecated Use {@link #open(String)}
     */
    @Deprecated(forRemoval = true, since = "0.5.2")
    @Override
    default Optional<InputStream> openResource(final String path) {
        return this.open(path);
    }
}
