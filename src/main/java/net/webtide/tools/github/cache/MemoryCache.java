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

package net.webtide.tools.github.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.webtide.tools.github.Cache;
import net.webtide.tools.github.GitHubResourceNotFoundException;

public class MemoryCache implements Cache
{
    private Set<String> notFoundSet = new HashSet<>();
    private Map<String, String> cached = new HashMap<>();

    @Override
    public String getCached(String path) throws IOException
    {
        if (notFoundSet.contains(path))
            throw new GitHubResourceNotFoundException(path);

        return cached.get(path);
    }

    @Override
    public void saveNotFound(String path)
    {
        notFoundSet.add(path);
    }

    @Override
    public void save(String path, String body)
    {
        cached.put(path, body);
    }
}
