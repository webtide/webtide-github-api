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
import java.util.function.Function;

public class Card
{

    protected String note;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
    protected Issue issue;
    protected String content_url;

    public String getNote()
    {
        return note;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public Issue getIssue()
    {
        try
        {
            return GitHubApi.connect().query(content_url, Issue.class, builder -> builder.GET()
                .header("Accept", "application/vnd.github.v3+json")
                .build());
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
