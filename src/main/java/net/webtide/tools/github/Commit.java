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

import java.util.ArrayList;
import java.util.List;

public class Commit
{
    public static class Details
    {
        protected Authorship author;
        protected Authorship committer;
        protected String message;
        protected Verification verification;

        public Authorship getAuthor()
        {
            return author;
        }

        public Authorship getCommitter()
        {
            return committer;
        }

        public String getMessage()
        {
            return message;
        }

        public Verification getVerification()
        {
            return verification;
        }
    }

    protected String sha;
    protected Commit.Details commit;
    protected User author;
    protected User committer;
    protected List<Sha> parents = new ArrayList<>();
    protected List<FileDiff> files = new ArrayList<>();

    public String getSha()
    {
        return sha;
    }

    public Details getCommit()
    {
        return commit;
    }

    public User getAuthor()
    {
        return author;
    }

    public User getCommitter()
    {
        return committer;
    }

    public List<Sha> getParents()
    {
        return parents;
    }

    public List<FileDiff> getFiles()
    {
        return files;
    }
}
