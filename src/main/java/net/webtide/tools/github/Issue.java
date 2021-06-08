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
import java.util.ArrayList;
import java.util.List;

public class Issue
{
    protected int number;
    protected String title;
    protected String state;
    protected List<Label> labels = new ArrayList<>();
    protected User user;
    protected User closedBy;
    protected List<User> assignees = new ArrayList<>();
    protected String body;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
    protected ZonedDateTime closedAt;
    protected String authorAssociation;
    protected PullRequestRef pullRequest;

    public int getNumber()
    {
        return number;
    }

    public String getTitle()
    {
        return title;
    }

    public String getState()
    {
        return state;
    }

    public User getUser()
    {
        return user;
    }

    public List<Label> getLabels()
    {
        return labels;
    }

    public List<User> getAssignees()
    {
        return assignees;
    }

    public String getBody()
    {
        return body;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public ZonedDateTime getClosedAt()
    {
        return closedAt;
    }

    public String getAuthorAssociation()
    {
        return authorAssociation;
    }

    public User getClosedBy()
    {
        return closedBy;
    }

    public PullRequestRef getPullRequest()
    {
        return pullRequest;
    }

    @Override
    public String toString()
    {
        return "Issue{" + "number=" + number + ", title='" + title + '\'' + ", state='" + state + '\'' + ", labels="
            + labels + ", user=" + user + ", closedBy=" + closedBy + ", assignees=" + assignees + ", body='" + body
            + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", closedAt=" + closedAt
            + ", authorAssociation='" + authorAssociation + '\'' + ", pullRequest=" + pullRequest + '}';
    }
}
