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

public class CrossReference
{
    protected ZonedDateTime createdAt;
    protected ZonedDateTime mergedAt;
    protected Ref baseRef;
    protected User mergedBy;
    protected String title;
    protected String state;
    protected String url;

    public Ref getBaseRef()
    {
        return baseRef;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public ZonedDateTime getMergedAt()
    {
        return mergedAt;
    }

    public User getMergedBy()
    {
        return mergedBy;
    }

    public String getState()
    {
        return state;
    }

    public String getTitle()
    {
        return title;
    }

    public String getUrl()
    {
        return url;
    }
}
