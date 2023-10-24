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
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import net.webtide.tools.github.cache.PersistentCache;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;
import org.eclipse.jetty.toolchain.test.jupiter.WorkDir;
import org.eclipse.jetty.toolchain.test.jupiter.WorkDirExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(WorkDirExtension.class)
public class IssueTimelineTest
{
    @Test
    public void testIssueTimeline10330(WorkDir workDir) throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        github.setCache(new PersistentCache(workDir.getEmptyPathDir()));
        IssueTimeline timeline = github.issueTimeline("jetty", "jetty.project", 10330);
        assertNotNull(timeline);
    }

    @Test
    public void testLoadIssueTimelineJson() throws IOException
    {
        Path json = MavenTestingUtils.getTestResourcePathFile("github/issue-timeline-10330.json");
        Gson gson = GitHubApi.newGson();
        try (BufferedReader reader = Files.newBufferedReader(json))
        {
            IssueTimeline timeline = gson.fromJson(reader, IssueTimeline.class);
            assertNotNull(timeline);
            assertEquals(30, timeline.size());

            List<IssueTimeline.Event> events = timeline.stream()
                .filter(event -> event.source != null)
                .collect(Collectors.toList());
            assertEquals(1, events.size(), "Should have only 1 event with a source");
        }
    }
}
