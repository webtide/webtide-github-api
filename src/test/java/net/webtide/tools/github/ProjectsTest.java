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

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectsTest
{

    private static final Logger LOG = LoggerFactory.getLogger(ProjectsTest.class);

    private static String REPO_OWNER;
    private static String REPO_NAME;

    @BeforeAll
    public static void setup() throws Exception
    {
        // we need to find the project we want to test
        String ghRepo = System.getenv().get("GITHUB_REPOSITORY");
        if (ghRepo != null && !ghRepo.isEmpty()) // are we running gh action
        {
            // we receive something such eclipse/jetty.project
            String[] parts = ghRepo.split("/");
            REPO_OWNER = parts[0];
            REPO_NAME = parts[1];
        }
        else
        {
            // let's try to find repo from local git
            FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
            Repository repository = repositoryBuilder.setWorkTree(new File("."))
                .readEnvironment()
                .findGitDir()
                .setMustExist(true)
                .build();
            String gitUrl = repository.getConfig().getString("remote", "origin", "url" );
            // format is the raw string from .git/config > [remote "origin"] > url
            if (gitUrl != null && !gitUrl.isEmpty())
            {
                if (gitUrl.startsWith("git@github.com:"))
                {
                    // format is git@github.com:<owner>/<repo-name>.git
                    gitUrl = gitUrl.replaceFirst("^git@github.com:", "");
                    gitUrl = gitUrl.replaceFirst("\\.git$", "");
                    int idxSep = gitUrl.indexOf('/');
                    REPO_OWNER = gitUrl.substring(0, idxSep);
                    REPO_NAME = gitUrl.substring(idxSep + 1);
                }
                else if (gitUrl.startsWith("https://github.com/"))
                {
                    // format is https://github.com/<owner>/<repo-name>
                    gitUrl = gitUrl.replaceFirst("^https://github.com/", "");
                    int idxSep = gitUrl.lastIndexOf('/');
                    REPO_OWNER = gitUrl.substring(0, idxSep);
                    REPO_NAME = gitUrl.substring(idxSep + 1);
                }
                else
                {
                    LOG.error("Unrecognized URI format: " + gitUrl);
                    throw new RuntimeException("Unrecognized URI format: " + gitUrl);
                }
            }
            else
            {
                LOG.error("cannot find repoOwner/repoName for testing");
                throw new RuntimeException("cannot find repoOwner/repoName for testing");
            }
        }

        LOG.info("use repoOwner/repoName {}/{} for testing", REPO_OWNER, REPO_NAME);
    }

    @Test
    public void testProjects() throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        Projects projects = github.getGitHubProjectApi().listProjects(REPO_OWNER, REPO_NAME, 0, 0);

        Stream<Project> streamProjects = github.getGitHubProjectApi().streamProjects("eclipse", "jetty.project", 0);
        LOG.info("projects list size {}, stream size: {}",projects.size(), streamProjects.count());

        LOG.info("projects: {}", projects);

        Project project = projects.stream().filter(p -> p.getName().contains("1.0.0-test")).findFirst().get();

        Columns columns = github.getGitHubColumnsApi().listColumns(project, 0, 0);
        LOG.info("columns: {}", columns);

        columns = github.getGitHubColumnsApi().listColumns(project, 0, 0);
        LOG.info("columns: {}", columns);
        Stream<Column> streamColumns = github.getGitHubColumnsApi().streamColumns(project, 0);
        LOG.info("columns list size {}, stream size: {}",columns.size(), streamColumns.count());

        Column column = columns.get(0);

        Cards cards = github.getGitHubCardsApi().listCards(column, 0, 0);
        Stream<Card> streamCards = github.getGitHubCardsApi().streamCards(column, 0);
        LOG.info("cards list size {}, stream size: {}",cards.size(), streamCards.count());
        LOG.info("cards: {}", cards);
        for (Card card : cards)
        {
            LOG.info("card: {}, issue: {}", card, github.getIssueFromCard(card));
        }
    }

}
