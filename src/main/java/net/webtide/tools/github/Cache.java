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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Cache
{
    private final Path root;

    public Cache()
    {
        this(Paths.get(System.getProperty("user.home"), ".cache", "api.github.com"));
    }

    public Cache(Path cacheDir)
    {
        this.root = cacheDir;
        if (!Files.exists(this.root))
        {
            try
            {
                Files.createDirectories(this.root);
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to create cache dir: " + this.root, e);
            }
        }
    }

    private Path toJsonPath(String path)
    {
        String relativePath = path;
        if (relativePath.startsWith("/"))
            relativePath = relativePath.substring(1);

        return this.root.resolve(relativePath + ".json");
    }

    public String getCached(String path) throws IOException
    {
        // TODO: expire file
        byte[] buf = Files.readAllBytes(toJsonPath(path));
        String body = new String(buf, UTF_8);
        if (body.equals("-"))
            throw new GitHubResourceNotFoundException(path);
        return body;
    }

    public void saveNotFound(String path) throws IOException
    {
        save(path, "-");
    }

    public void save(String path, String body) throws IOException
    {
        Path destFile = toJsonPath(path);
        Path parentDir = destFile.getParent();
        if (!Files.exists(parentDir))
        {
            Files.createDirectories(parentDir);
        }
        Files.writeString(toJsonPath(path), body, UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }
}
