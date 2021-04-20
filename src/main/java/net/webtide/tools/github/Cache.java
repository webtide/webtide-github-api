//
// ========================================================================
// Copyright (c) Webtide LLC and others.
//
// This program and the accompanying materials are made available under the
// terms of the Apache License, Version 2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0.
//
// SPDX-License-Identifier: Apache-2.0
// ========================================================================
//

package net.webtide.tools.github;

import java.io.IOException;

public interface Cache
{
    /**
     * Get the Cached contents of the path provided.
     *
     * @param path the path
     * @return the contents, or null if not in cache.
     * @throws GitHubResourceNotFoundException if path is known, and is recorded as not found.
     * @throws IOException if unable to load cached contents.
     */
    String getCached(String path) throws GitHubResourceNotFoundException, IOException;

    /**
     * Save to the cache a not found path.
     * <p>
     * Will result in a GitHubResourceNotFoundException for all requests for the same path on {@link #getCached(String)}
     * </p>
     *
     * @param path the path that wasn't found
     * @throws IOException if unable to save the cache entry
     */
    void saveNotFound(String path) throws IOException;

    /**
     * Save to the cache the body content.
     *
     * @param path the path to cache
     * @param body the contents to cache
     * @throws IOException if unable to save the cache entry
     */
    void save(String path, String body) throws IOException;
}
