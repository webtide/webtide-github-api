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

public class FileDiff
{
    protected String sha;
    protected String filename;
    protected String state;
    protected int additions;
    protected int deletions;
    protected int changes;
    protected String patch;
}
