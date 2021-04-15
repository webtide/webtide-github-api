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

public class ListReleasesSpliterator implements Spliterator<Release>
{
    private final GitHubApi github;
    private final String repoOwner;
    private final String repoName;
    private final int perPage;
    private final List<Release> activeReleases = new ArrayList<>();
    private int activeOffset = Integer.MAX_VALUE; // already past end at start, to trigger fetch of next releases page
    private int activePage = 1;

    public ListReleasesSpliterator(GitHubApi gitHubApi, String repoOwner, String repoName, int perPage)
    {
        this.github = gitHubApi;
        this.repoOwner = repoOwner;
        this.repoName = repoName;
        this.perPage = perPage;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Release> action)
    {
        Release release = getNextRelease();
        if (release == null)
            return false;
        else
        {
            action.accept(release);
            return true;
        }
    }

    private Release getNextRelease()
    {
        if (activeOffset >= activeReleases.size())
        {
            try
            {
                activeReleases.clear();
                while (activeReleases.isEmpty())
                {
                    Releases releases = github.listReleases(repoOwner, repoName, perPage, activePage++);
                    if ((releases == null) || releases.isEmpty())
                        return null;
                    activeReleases.addAll(releases);
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

        if (activeReleases.isEmpty())
        {
            return null;
        }

        return activeReleases.get(activeOffset++);
    }

    @Override
    public Spliterator<Release> trySplit()
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
