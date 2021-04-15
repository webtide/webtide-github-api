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
import static org.junit.jupiter.api.Assertions.assertNull;

public class ChangeIssueTest
{
    @Test
    public void testIssue5675() throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        Issue issue = github.issue("eclipse", "jetty.project", 5675);

        assertNotNull(issue);
        assertEquals(5675, issue.number);
        assertEquals("janbartel", issue.user.login);
        assertEquals("closed", issue.state);
        assertNull(issue.pullRequest);
    }

    @Test
    public void testPullRequest5676() throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        Issue issue = github.issue("eclipse", "jetty.project", 5676);

        assertNotNull(issue);
        assertEquals(5676, issue.number);
        assertEquals("janbartel", issue.user.login);
        assertEquals("closed", issue.state);
        assertNotNull(issue.pullRequest);
    }

    @Test
    public void testLoadIssueJson() throws IOException
    {
        Path json = MavenTestingUtils.getTestResourcePathFile("github/issue-eclipse-jetty.project-5675.json");
        Gson gson = GitHubApi.newGson();
        try (BufferedReader reader = Files.newBufferedReader(json))
        {
            Issue issue = gson.fromJson(reader, Issue.class);
            assertNotNull(issue);
            assertEquals(5675, issue.number);
            assertEquals("janbartel", issue.user.login);
            assertEquals("open", issue.state);
            assertNull(issue.pullRequest);
        }
    }

    @Test
    public void testLoadPRJson() throws IOException
    {
        Path json = MavenTestingUtils.getTestResourcePathFile("github/issue-eclipse-jetty.project-5676.json");
        Gson gson = GitHubApi.newGson();
        try (BufferedReader reader = Files.newBufferedReader(json))
        {
            Issue issue = gson.fromJson(reader, Issue.class);
            assertNotNull(issue);
            assertEquals(5676, issue.number);
            assertEquals("janbartel", issue.user.login);
            assertEquals("closed", issue.state);
            assertNotNull(issue.pullRequest);
        }
    }
}
