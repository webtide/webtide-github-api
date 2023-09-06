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
import java.util.Map;

public class IssueTimeline extends ArrayList<IssueTimeline.Event>
{
    public static class Event
    {
        protected User actor;
        protected String event;
        protected String commitId;
        protected ZonedDateTime createdAt;
        protected ZonedDateTime updatedAt;
        protected Map<String, Object> source;
    }
}
