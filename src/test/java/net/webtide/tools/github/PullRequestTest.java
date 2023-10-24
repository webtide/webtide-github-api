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

public class PullRequestTest
{
    @Test
    public void testPullRequest5676() throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        PullRequest pullRequest = github.pullRequest("jetty", "jetty.project", 5676);

        assertNotNull(pullRequest);
        assertEquals(5676, pullRequest.number);
        assertEquals("janbartel", pullRequest.user.login);
        assertEquals("jetty-9.4.x-5675-update-osgi-test-deps", pullRequest.head.ref);
        assertEquals("jetty-9.4.x", pullRequest.base.ref);
        assertEquals("closed", pullRequest.state);
    }

    @Test
    public void testLoadPullRequestJson() throws IOException
    {
        Path json = MavenTestingUtils.getTestResourcePathFile("github/pull-request-5676.json");
        Gson gson = GitHubApi.newGson();
        try (BufferedReader reader = Files.newBufferedReader(json))
        {
            PullRequest pullRequest = gson.fromJson(reader, PullRequest.class);
            assertNotNull(pullRequest);
            assertEquals(5676, pullRequest.number);
            assertEquals("janbartel", pullRequest.user.login);
            assertEquals("jetty-9.4.x-5675-update-osgi-test-deps", pullRequest.head.ref);
            assertEquals("jetty-9.4.x", pullRequest.base.ref);
            assertEquals("closed", pullRequest.state);
        }
    }
}
