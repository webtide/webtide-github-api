package net.webtide.tools.github;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
        Stream<Repository> repositoryStream = github.streamRepositories("webtide", 10);

        assertNotNull(repositoryStream);
        Set<String> repoNames = new HashSet<>();
        repositoryStream.forEach((repo) ->
        {
            System.out.printf("%s - %s [%s]%n", repo.getName(), repo.getUpdatedAt(), getFlags(repo));
            repoNames.add(repo.getName());
        });
        assertThat(repoNames, Matchers.hasItem("logos"));
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
