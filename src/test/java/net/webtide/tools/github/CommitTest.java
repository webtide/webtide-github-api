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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommitTest
{
    @Test
    public void testCommit() throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        Commit commit = github.commit("eclipse", "jetty.project", "5630929549a0116b977defe25d0c54de27fc10a0");
        assertNotNull(commit);
        assertEquals("5630929549a0116b977defe25d0c54de27fc10a0", commit.sha);
        assertEquals("igalic", commit.author.login);
        assertEquals(1, commit.files.size());
    }

    @Test
    public void testLoadJson() throws IOException
    {
        Path json = MavenTestingUtils.getTestResourcePathFile("github/commit.json");
        Gson gson = GitHubApi.newGson();
        try (BufferedReader reader = Files.newBufferedReader(json))
        {
            Commit commit = gson.fromJson(reader, Commit.class);
            assertNotNull(commit);
            assertEquals("9c3cfb92f93e5c5f2c54ac5509b87fd39d201f22", commit.sha);
            assertEquals("attiand", commit.author.login);
            assertEquals(4, commit.files.size());
        }
    }
}
