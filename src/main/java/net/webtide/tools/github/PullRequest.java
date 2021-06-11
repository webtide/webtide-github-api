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

public class PullRequest extends Issue
{
    protected boolean locked;
    protected ZonedDateTime mergedAt;
    protected String mergeCommitSha;
    protected List<User> requestedReviewers = new ArrayList<>();
    protected BaseHeadRef head;
    protected BaseHeadRef base;
    protected boolean merged;
    protected User mergedBy;
    protected int comments;
    protected int reviewComments;
    protected int commits;
    protected int additions;
    protected int deletions;
    protected int changed_files;
    protected boolean draft;

    public int getAdditions()
    {
        return additions;
    }

    public BaseHeadRef getBase()
    {
        return base;
    }

    public int getChanged_files()
    {
        return changed_files;
    }

    public int getComments()
    {
        return comments;
    }

    public int getCommits()
    {
        return commits;
    }

    public int getDeletions()
    {
        return deletions;
    }

    public BaseHeadRef getHead()
    {
        return head;
    }

    public List<Label> getLabels()
    {
        return labels;
    }

    public String getMergeCommitSha()
    {
        return mergeCommitSha;
    }

    public ZonedDateTime getMergedAt()
    {
        return mergedAt;
    }

    public User getMergedBy()
    {
        return mergedBy;
    }

    public List<User> getRequestedReviewers()
    {
        return requestedReviewers;
    }

    public int getReviewComments()
    {
        return reviewComments;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public boolean isMerged()
    {
        return merged;
    }

    public boolean isDraft()
    {
        return draft;
    }
}
