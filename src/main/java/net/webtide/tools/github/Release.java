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

import java.time.ZonedDateTime;

public class Release
{
    protected int id;
    protected String tagName;
    protected String targetCommitish;
    protected String name;
    protected String body;
    protected boolean draft;
    protected boolean prerelease;
    protected User author;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime publishedAt;

    public int getId()
    {
        return id;
    }

    public String getTagName()
    {
        return tagName;
    }

    public String getTargetCommitish()
    {
        return targetCommitish;
    }

    public String getName()
    {
        return name;
    }

    public String getBody()
    {
        return body;
    }

    public boolean isDraft()
    {
        return draft;
    }

    public boolean isPrerelease()
    {
        return prerelease;
    }

    public User getAuthor()
    {
        return author;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public ZonedDateTime getPublishedAt()
    {
        return publishedAt;
    }
}
