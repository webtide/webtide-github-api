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

public class IssueEvents extends ArrayList<IssueEvents.IssueEvent>
{
    public static class IssueEvent
    {
        protected String event;
        protected User actor;
        protected User assignee;
        protected String commitId;
        protected ZonedDateTime createdAt;

        public String getEvent()
        {
            return event;
        }

        public User getActor()
        {
            return actor;
        }

        public User getAssignee()
        {
            return assignee;
        }

        public String getCommitId()
        {
            return commitId;
        }

        public ZonedDateTime getCreatedAt()
        {
            return createdAt;
        }
    }
}
