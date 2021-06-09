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

import net.webtide.tools.github.Card;
import net.webtide.tools.github.Cards;
import net.webtide.tools.github.Column;
import net.webtide.tools.github.GitHubApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ListCardsSpliterator implements Spliterator<Card>
{
    private final GitHubApi github;
    private final Column column;
    private final List<Card> activeCards = new ArrayList<>();
    private int activeOffset = Integer.MAX_VALUE; // already past end at start, to trigger fetch of next releases page
    private int activePage = 1;
    private final int perPage;

    public ListCardsSpliterator(GitHubApi gitHubApi, Column column, int perPage)
    {
        this.github = gitHubApi;
        this.column = column;
        this.perPage = perPage;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Card> action)
    {
        Card card = getNextCard();
        if (card == null)
            return false;
        else
        {
            action.accept(card);
            return true;
        }
    }

    private Card getNextCard()
    {
        if (activeOffset >= activeCards.size())
        {
            try
            {
                activeCards.clear();
                while (activeCards.isEmpty())
                {
                    Cards cards = github.listCards( column, perPage, activePage++);
                    if ((cards == null) || cards.isEmpty())
                        return null;
                    activeCards.addAll(cards);
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

        if (activeCards.isEmpty())
        {
            return null;
        }

        return activeCards.get(activeOffset++);
    }

    @Override
    public Spliterator<Card> trySplit()
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
