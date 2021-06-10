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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GitHubCardsApi
{

    private final GitHubApi gitHubApi;

    public GitHubCardsApi( GitHubApi gitHubApi)
    {
        this.gitHubApi = gitHubApi;
    }


    public Cards listCards(Column column, int resultsPerPage, int pageNum) throws IOException, InterruptedException
    {
        GitHubApi.Query query = new GitHubApi.Query();
        if (resultsPerPage > 0) query.put("per_page", String.valueOf(resultsPerPage));
        if (pageNum > 0) query.put("page", String.valueOf(pageNum));
        String path = String.format("https://api.github.com/projects/columns/%s/cards?%s", column.getId(), query.toEncodedQuery());

        String body = this.gitHubApi.getCachedBody(path, (requestBuilder) ->
            requestBuilder.GET()
                .header("Accept", "application/vnd.github.inertia-preview+json")
                .build());
        return this.gitHubApi.getGson().fromJson(body, Cards.class);
    }


    public Stream<Card> streamCards(Column column, int resultsPerPage)
    {
        ListSplitIterator.DataSupplier dataSupplier =
            activePage -> listCards(column, resultsPerPage, activePage);
        return StreamSupport.stream(new ListSplitIterator<Card>(this.gitHubApi, dataSupplier), false);
    }

}
