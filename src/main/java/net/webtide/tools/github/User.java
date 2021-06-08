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
import java.util.Map;

public class User
{
    protected String login;
    protected String type;
    protected String name;
    protected String company;
    protected String location;
    protected String email;
    protected String bio;
    protected boolean siteAdmin;
    protected Map<String, Boolean> permissions;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;

    public String getLogin()
    {
        return login;
    }

    public String getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public String getCompany()
    {
        return company;
    }

    public String getLocation()
    {
        return location;
    }

    public String getEmail()
    {
        return email;
    }

    public String getBio()
    {
        return bio;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public boolean isSiteAdmin()
    {
        return siteAdmin;
    }

    public Map<String, Boolean> getPermissions()
    {
        return permissions;
    }

    @Override
    public String toString()
    {
        return "User{" + "login='" + login + '\'' + ", type='" + type + '\'' + ", name='" + name + '\'' + ", company='"
            + company + '\'' + ", location='" + location + '\'' + ", email='" + email + '\'' + ", bio='" + bio + '\''
            + ", siteAdmin=" + siteAdmin + ", permissions=" + permissions + ", createdAt=" + createdAt + ", updatedAt="
            + updatedAt + '}';
    }
}
