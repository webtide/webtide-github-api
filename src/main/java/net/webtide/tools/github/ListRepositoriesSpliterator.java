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
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ListRepositoriesSpliterator implements Spliterator<Repository>
{
    private final GitHubApi github;
    private final String repoOwner;
    private final int perPage;
    private final List<Repository> activeRepositories = new ArrayList<>();
    private int activeOffset = Integer.MAX_VALUE; // already past end at start, to trigger fetch of next releases page
    private int activePage = 1;

    public ListRepositoriesSpliterator(GitHubApi gitHubApi, String repoOwner, int perPage)
    {
        this.github = gitHubApi;
        this.repoOwner = repoOwner;
        this.perPage = perPage;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Repository> action)
    {
        Repository repository = getNextRepository();
        if (repository == null)
            return false;
        else
        {
            action.accept(repository);
            return true;
        }
    }

    private Repository getNextRepository()
    {
        if (activeOffset >= activeRepositories.size())
        {
            try
            {
                activeRepositories.clear();
                while (activeRepositories.isEmpty())
                {
                    Repositories releases = github.listRepositories(repoOwner, perPage, activePage++);
                    if ((releases == null) || releases.isEmpty())
                        return null;
                    activeRepositories.addAll(releases);
                    activeOffset = 0;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace(System.err);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        if (activeRepositories.isEmpty())
        {
            return null;
        }

        return activeRepositories.get(activeOffset++);
    }

    @Override
    public Spliterator<Repository> trySplit()
    {
        return null;
    }

    @Override
    public long estimateSize()
    {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics()
    {
        return ORDERED;
    }
}
