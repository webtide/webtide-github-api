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

public class BaseHeadRef
{
    protected String label;
    protected String ref;
    protected String sha;
    protected User user;
    protected Repository repo;

    public String getLabel()
    {
        return label;
    }

    public String getRef()
    {
        return ref;
    }

    public String getSha()
    {
        return sha;
    }

    public User getUser()
    {
        return user;
    }

    public Repository getRepo()
    {
        return repo;
    }
}
