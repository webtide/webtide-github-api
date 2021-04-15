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

import com.google.gson.annotations.SerializedName;

public class Repository
{
    protected String name;
    protected String fullName;
    @SerializedName("private")
    protected boolean privateFlag;
    protected User owner;
    protected String description;
    protected boolean fork;
    protected String url;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
    protected ZonedDateTime pushedAt;
    protected int stargazersCount;
    protected int watchersCount;
    protected int forksCount;
    protected String language;
    protected long size;
    protected String mirrorUrl;
    protected boolean disabled;
    protected boolean archived;
    protected int openIssuesCount;
    protected String defaultBranch;

    public String getName()
    {
        return name;
    }

    public String getFullName()
    {
        return fullName;
    }

    public boolean isPrivateFlag()
    {
        return privateFlag;
    }

    public User getOwner()
    {
        return owner;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isFork()
    {
        return fork;
    }

    public String getUrl()
    {
        return url;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public ZonedDateTime getPushedAt()
    {
        return pushedAt;
    }

    public int getStargazersCount()
    {
        return stargazersCount;
    }

    public int getWatchersCount()
    {
        return watchersCount;
    }

    public int getForksCount()
    {
        return forksCount;
    }

    public String getLanguage()
    {
        return language;
    }

    public long getSize()
    {
        return size;
    }

    public String getMirrorUrl()
    {
        return mirrorUrl;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public boolean isArchived()
    {
        return archived;
    }

    public int getOpenIssuesCount()
    {
        return openIssuesCount;
    }

    public String getDefaultBranch()
    {
        return defaultBranch;
    }
}
