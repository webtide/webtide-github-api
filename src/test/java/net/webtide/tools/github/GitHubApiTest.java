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

import org.junit.jupiter.api.Test;

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
        // System.out.println(body);
    }

    @Test
    public void testShowEnv()
    {
        System.getenv().forEach((key, value) -> System.out.printf("[%s] -> [%s]%n", key, value));
    }
}
