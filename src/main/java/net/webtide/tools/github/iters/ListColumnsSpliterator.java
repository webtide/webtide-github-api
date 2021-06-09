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

package net.webtide.tools.github.iters;

import net.webtide.tools.github.Column;
import net.webtide.tools.github.Columns;
import net.webtide.tools.github.GitHubApi;
import net.webtide.tools.github.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ListColumnsSpliterator implements Spliterator<Column>
{
    private final GitHubApi github;
    private final Project project;
    private final List<Column> activeColumns = new ArrayList<>();
    private int activeOffset = Integer.MAX_VALUE; // already past end at start, to trigger fetch of next releases page
    private int activePage = 1;
    private final int perPage;

    public ListColumnsSpliterator(GitHubApi gitHubApi, Project project, int perPage)
    {
        this.github = gitHubApi;
        this.project = project;
        this.perPage = perPage;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Column> action)
    {
        Column column = getNextColumn();
        if (column == null)
            return false;
        else
        {
            action.accept(column);
            return true;
        }
    }

    private Column getNextColumn()
    {
        if (activeOffset >= activeColumns.size())
        {
            try
            {
                activeColumns.clear();
                while (activeColumns.isEmpty())
                {
                    Columns columns = github.listColumns(project, perPage, activePage++);
                    if ((columns == null) || columns.isEmpty())
                        return null;
                    activeColumns.addAll(columns);
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

        if (activeColumns.isEmpty())
        {
            return null;
        }

        return activeColumns.get(activeOffset++);
    }

    @Override
    public Spliterator<Column> trySplit()
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
