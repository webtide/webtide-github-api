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
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class GitHubApiTest
{
    @Test
    public void testRaw() throws IOException, InterruptedException
    {
        GitHubApi api = GitHubApi.connect();
        String path = "/repos/eclipse/jetty.project/pulls/5676/commits";
        String body = api.raw(path, (requestBuilder) ->
            requestBuilder.GET()
                .header("Accept", "application/vnd.github.v3+json")
                .build()
        );
    }

    @Test
    public void testLoadQuery() throws IOException
    {
        Map<String, String> optionMap = new HashMap<>();
        optionMap.put("OWNER", "eclipse");
        optionMap.put("REPOSITORY", "jetty.project");
        int issueNum = 10330;
        optionMap.put("ISSUENUM", Integer.toString(issueNum));
        String query = GitHubApi.loadQuery("/graphql-templates/query-prs-linked-to-issue.graphql", optionMap);
        // System.out.println(query);
        assertFalse(query.contains("@"));
    }

    @Test
    @Disabled
    public void testShowEnv()
    {
        System.getenv().forEach((key, value) -> System.out.printf("[%s] -> [%s]%n", key, value));
    }
}
