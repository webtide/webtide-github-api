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

public class PullRequestCommitsTest
{
    @Test
    public void testPullRequest5676Commits() throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        PullRequestCommits commits = github.pullRequestCommits("jetty", "jetty.project", 5676);

        assertNotNull(commits);
        assertEquals(8, commits.size());
        PullRequestCommits.Commit commit = commits.get(0);
        assertEquals("janb@webtide.com", commit.commit.author.email);
        assertEquals("bdb4dd435e18336c61f67d270004e17696481bc3", commit.sha);
    }

    @Test
    public void testLoadPullRequestCommitsJson() throws IOException
    {
        Path json = MavenTestingUtils.getTestResourcePathFile("github/pull-request-5676-commits.json");
        Gson gson = GitHubApi.newGson();
        try (BufferedReader reader = Files.newBufferedReader(json))
        {
            PullRequestCommits commits = gson.fromJson(reader, PullRequestCommits.class);
            assertNotNull(commits);
            assertEquals(8, commits.size());
            PullRequestCommits.Commit commit = commits.get(0);
            assertEquals("janb@webtide.com", commit.commit.author.email);
            assertEquals("bdb4dd435e18336c61f67d270004e17696481bc3", commit.sha);
        }
    }
}
