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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import net.webtide.tools.github.cache.PersistentCache;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RepositoryTest
{
    @Test
    public void listRepositoriesTest()
    {
        GitHubApi github = GitHubApi.connect();
        github.setCache(new PersistentCache(MavenTestingUtils.getTargetTestingPath()));
        Stream<Repository> repositoryStream = github.streamRepositories("webtide", 10);

        assertNotNull(repositoryStream);
        Set<String> repoNames = new HashSet<>();
        repositoryStream.forEach((repo) ->
        {
            // System.out.printf("%s - %s [%s]%n", repo.getName(), repo.getUpdatedAt(), getFlags(repo));
            repoNames.add(repo.getName());
        });
        assertThat(repoNames, Matchers.hasItem("logos"));
    }

    @Test
    public void listRepositoryCollaborators()
    {
        GitHubApi github = GitHubApi.connect();
        github.setCache(new PersistentCache(MavenTestingUtils.getTargetTestingPath()));
        Stream<User> collaborators = github.streamRepositoryCollaborators("webtide", "jenkins-slave", 10);

        assertNotNull(collaborators);
        Set<String> userNames = new HashSet<>();
        collaborators.forEach((collaborator) ->
        {
            // System.out.printf("%s - [%s]%n", collaborator.getLogin(), getPermissions(collaborator));
            userNames.add(collaborator.getName());
        });
    }

    private String getPermissions(User collaborator)
    {
        List<String> perms = new ArrayList<>();
        if (collaborator.isSiteAdmin())
            perms.add("SITE_ADMIN");
        if (collaborator.getPermissions() != null)
        {
            for (Map.Entry<String, Boolean> entry : collaborator.getPermissions().entrySet())
            {
                if (entry.getValue())
                {
                    perms.add("perm." + entry.getKey());
                }
            }
        }
        return String.join(", ", perms);
    }

    private String getFlags(Repository repo)
    {
        List<String> flags = new ArrayList<>();
        if (repo.isPrivateFlag())
            flags.add("PRIVATE");
        if (repo.isArchived())
            flags.add("ARCHIVED");
        if (repo.isDisabled())
            flags.add("DISABLED");
        if (repo.isFork())
            flags.add("FORK");
        return String.join(", ", flags);
    }
}
