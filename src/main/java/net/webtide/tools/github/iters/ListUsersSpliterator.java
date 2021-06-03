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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import net.webtide.tools.github.User;
import net.webtide.tools.github.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUsersSpliterator implements Spliterator<User>
{
    public interface Function
    {
        Users getUsers(int page) throws IOException, InterruptedException;
    }

    private static final Logger LOG = LoggerFactory.getLogger(ListUsersSpliterator.class);
    private final Function listUsersFunction;
    private final List<User> activeUsers = new ArrayList<>();
    private int activeOffset = Integer.MAX_VALUE; // already past end at start, to trigger fetch of next releases page
    private int activePage = 1;

    public ListUsersSpliterator(Function listUsersFunction)
    {
        this.listUsersFunction = listUsersFunction;
    }

    @Override
    public boolean tryAdvance(Consumer<? super User> action)
    {
        User user = getNextUser();
        if (user == null)
            return false;
        else
        {
            action.accept(user);
            return true;
        }
    }

    private User getNextUser()
    {
        if (activeOffset >= activeUsers.size())
        {
            try
            {
                activeUsers.clear();
                while (activeUsers.isEmpty())
                {
                    Users users = listUsersFunction.getUsers(activePage++);
                    if ((users == null) || users.isEmpty())
                        return null;
                    activeUsers.addAll(users);
                    activeOffset = 0;
                }
            }
            catch (Throwable cause)
            {
                LOG.debug("Unable to list users", cause);
            }
        }

        if (activeUsers.isEmpty())
        {
            return null;
        }

        return activeUsers.get(activeOffset++);
    }

    @Override
    public Spliterator<User> trySplit()
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
