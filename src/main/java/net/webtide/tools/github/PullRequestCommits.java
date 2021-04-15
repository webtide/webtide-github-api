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

public class PullRequestCommits extends ArrayList<PullRequestCommits.Commit>
{
    public static class Details
    {
        protected Authorship author;
        protected Authorship committer;
        protected String message;

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
    }

    public static class Commit
    {
        protected String sha;
        protected Details commit;
        protected List<Sha> parents = new ArrayList<>();

        public String getSha()
        {
            return sha;
        }

        public Details getCommit()
        {
            return commit;
        }

        public List<Sha> getParents()
        {
            return parents;
        }
    }
}
