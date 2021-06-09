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

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.stream.Stream;

public class ProjectsTest
{

    private static final Logger LOG = LoggerFactory.getLogger(ProjectsTest.class);

    @Test
    public void testProjects() throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        Projects projects = github.listProjects("eclipse", "jetty.project", 0, 0);

        Stream<Project> streamProjects = github.streamProjects("eclipse", "jetty.project", 0);
        LOG.info("projects list size {}, stream size: {}",projects.size(), streamProjects.count());

        LOG.info("projects: {}", projects);

        Project project = projects.stream().filter(p -> p.getName().contains("10.0.5")).findFirst().get();

        Columns columns = github.listColumns(project, 0, 0);
        LOG.info("columns: {}", columns);

        columns = github.listColumns(project, 0, 0);
        LOG.info("columns: {}", columns);
        Stream<Column> streamColumns = github.streamColumns(project, 0);
        LOG.info("columns list size {}, stream size: {}",columns.size(), streamColumns.count());

        Column column = columns.get(0);

        Cards cards = github.listCards(column, 0, 0);
        Stream<Card> streamCards = github.streamCards(column, 0);
        LOG.info("cards list size {}, stream size: {}",cards.size(), streamCards.count());
        LOG.info("cards: {}", cards);
        cards.forEach(card -> LOG.info("card: {}, issue: {}", card, card.getIssue()));
    }

}
