{
  search(
    last: 10
    type: ISSUE
    query: "repo:jetty-project/embedded-jetty-live-war type:PR label:dependencies created:>=2022-01-01"
  ) {
    issueCount
    discussionCount
    pageInfo {
      hasNextPage
      endCursor
    }
    edges {
      node {
        ... on PullRequest {
          number
          title
          url
          state
          createdAt
          mergedAt
          closedAt
          mergedBy {
            login
          }
          comments(last: 2) {
            edges {
              node {
                body
              }
            }
          }
        }
      }
    }
  }
}