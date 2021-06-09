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

import net.webtide.tools.github.GitHubApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ListSplitIterator<T> implements Spliterator<T>
{

    private final GitHubApi github;
    private final List<T> activeData = new ArrayList<>();
    private int activeOffset = Integer.MAX_VALUE; // already past end at start, to trigger fetch of next releases page
    private int activePage = 1;
    private final int perPage;
    private DataSupplier<T> supplier;

    public ListSplitIterator (GitHubApi gitHubApi, int perPage, DataSupplier<T> supplier)
    {
        this.github = gitHubApi;
        this.perPage = perPage;
        this.supplier = supplier;
    }

    @FunctionalInterface
    public interface DataSupplier<T> {
        List<T> get(int perPage, int activePage) throws IOException, InterruptedException;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action)
    {
        T data = getNextData();
        if (data == null)
            return false;
        else
        {
            action.accept(data);
            return true;
        }
    }

    private T getNextData()
    {
        if (activeOffset >= activeData.size())
        {
            try
            {
                activeData.clear();
                while (activeData.isEmpty())
                {
                    List<T> cards =  supplier.get(perPage, activePage++);
                    if ((cards == null) || cards.isEmpty())
                        return null;
                    activeData.addAll(cards);
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

        if (activeData.isEmpty())
        {
            return null;
        }

        return activeData.get(activeOffset++);
    }

    @Override
    public Spliterator<T> trySplit()
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
