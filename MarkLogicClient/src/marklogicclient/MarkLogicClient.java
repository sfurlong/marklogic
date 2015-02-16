package marklogicclient;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

public class MarkLogicClient {
  public MarkLogicClient() {
    super();
  }

  public void queryMarkLogic() {
    DatabaseClient client =
      DatabaseClientFactory.newClient("localhost", 8011, "admin", "skitaos1",
                                      Authentication.DIGEST);

    QueryManager queryMgr = client.newQueryManager();
    SearchHandle handle = new SearchHandle();

    StringQueryDefinition query = queryMgr.newStringDefinition();
    query.setCriteria("soul");
    queryMgr.search(query, handle);

    MatchDocumentSummary[] results = handle.getMatchResults();

    // Iterate over the results
    for (MatchDocumentSummary result : results) {
      // get the list of match locations for this result
      MatchLocation[] locations = result.getMatchLocations();
      // iterate over the match locations
      for (MatchLocation location : locations) {
        // iterate over the snippets at a match location
        for (MatchSnippet snippet : location.getSnippets()) {
          System.out.println(snippet.getText());
        }
      }
    }

  }

  public static void main(String[] args) {
    MarkLogicClient markLogicClient = new MarkLogicClient();
    System.out.println("Query Results:");
    markLogicClient.queryMarkLogic();
  }
}
