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
import java.time.ZonedDateTime;

public class Project
{

    protected long id;
    protected int number;
    protected String name;
    protected String body;
    protected String state;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
    protected User creator;

    public int getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public String getBody()
    {
        return body;
    }

    public String getState()
    {
        return state;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public User getCreator()
    {
        return creator;
    }

    public long getId()
    {
        return id;
    }


    @Override
    public String toString()
    {
        return "Project{" + "id=" + id + ", number=" + number + ", name='" + name + '\'' + ", body='" + body + '\''
            + ", state='" + state + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", creator="
            + creator + '}';
    }
}
