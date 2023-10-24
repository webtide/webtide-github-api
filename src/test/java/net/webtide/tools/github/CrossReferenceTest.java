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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.webtide.tools.github.cache.PersistentCache;
import net.webtide.tools.github.gson.ISO8601TypeAdapter;
import org.eclipse.jetty.toolchain.test.MavenPaths;
import org.eclipse.jetty.toolchain.test.jupiter.WorkDir;
import org.eclipse.jetty.toolchain.test.jupiter.WorkDirExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(WorkDirExtension.class)
public class CrossReferenceTest
{
    @Test
    public void testLoadCrossReference() throws IOException
    {
        Path rawResults = MavenPaths.findTestResourceFile("github/cross-reference.json");
        String rawJson = Files.readString(rawResults, StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(ZonedDateTime.class, new ISO8601TypeAdapter())
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();
        CrossReference crossReference = gson.fromJson(rawJson, CrossReference.class);

        assertEquals("MERGED", crossReference.getState());
        assertThat(crossReference.getTitle(), containsString("EE9"));
        assertNotNull(crossReference.getCreatedAt(), "createdAt");
        assertNotNull(crossReference.getBaseRef(), "baseRef");
        assertNotNull(crossReference.getUrl(), "url");
    }

    @Test
    public void testLoadCrossReferences() throws IOException
    {
        Path rawResults = MavenPaths.findTestResourceFile("github/graphql-result-issuetimeline-crossref-prs.json");
        String rawJson = Files.readString(rawResults, StandardCharsets.UTF_8);
        List<CrossReference> crossReferences = GitHubApi.loadCrossReferences(rawJson);
        assertNotNull(crossReferences);
        assertEquals(1, crossReferences.size());
        CrossReference ref1 = crossReferences.get(0);
        assertEquals("MERGED", ref1.getState());
        assertThat(ref1.getTitle(), containsString("EE9"));
        assertNotNull(ref1.getCreatedAt(), "createdAt");
        assertNotNull(ref1.getBaseRef(), "baseRef");
        assertNotNull(ref1.getUrl(), "url");
    }

    @Test
    public void testIssueCrossReference(WorkDir workDir) throws IOException, InterruptedException
    {
        GitHubApi github = GitHubApi.connect();
        github.setCache(new PersistentCache(workDir.getEmptyPathDir()));
        List<CrossReference> crossReferences = github.issueCrossReferences("jetty", "jetty.project", 10330);
        assertNotNull(crossReferences);
    }
}
