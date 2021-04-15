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

public class Authorship
{
    protected String name;
    protected String email;
    protected ZonedDateTime timestamp;

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public ZonedDateTime getTimestamp()
    {
        return timestamp;
    }
}
