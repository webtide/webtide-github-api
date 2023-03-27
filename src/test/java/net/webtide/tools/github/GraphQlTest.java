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

import net.webtide.tools.github.cache.PersistentCache;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;
import org.junit.jupiter.api.Test;

public class GraphQlTest
{
    @Test
    public void testDependabotPrQuery() throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        github.setCache(new PersistentCache(MavenTestingUtils.getTargetTestingPath()));

        boolean hasNextPage = true;
        String endCursor = null;

        while (hasNextPage)
        {
            String search = createSearchPrs(endCursor);
            String result = github.graphql(createSearchPrs(null));
            System.out.println(result);
        }
    }

    private String createSearchPrs(String endCursor)
    {
        StringBuilder search = new StringBuilder();
        search.append("{\n" +
            "  search(\n" +
            "    last: 10\n" +
            "    type: ISSUE\n");
        if (endCursor != null)
        {
            search.append("    after: \"").append(endCursor).append("\"\n");
        }
        search.append("    query: \"repo:jetty-project/embedded-jetty-live-war type:PR label:dependencies created:>=2022-01-01\"\n" +
            "  ) {\n" +
            "    issueCount\n" +
            "    discussionCount\n" +
            "    pageInfo {\n" +
            "      hasNextPage\n" +
            "      endCursor\n" +
            "    }\n" +
            "    edges {\n" +
            "      node {\n" +
            "        ... on PullRequest {\n" +
            "          number\n" +
            "          title\n" +
            "          url\n" +
            "          state\n" +
            "          createdAt\n" +
            "          mergedAt\n" +
            "          closedAt\n" +
            "          mergedBy {\n" +
            "            login\n" +
            "          }\n" +
            "          comments(last: 2) {\n" +
            "            edges {\n" +
            "              node {\n" +
            "                body\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}");
        return search.toString();
    }
}
