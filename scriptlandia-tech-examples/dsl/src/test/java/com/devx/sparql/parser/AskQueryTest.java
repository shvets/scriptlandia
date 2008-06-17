package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class AskQueryTest extends ParserTest {
    @Test
    public void noDatasetClauses() throws Exception {
        Tree tree = parseTreeFor( "ASK WHERE { ?s ?p ?o . } " );
        assertLeafNodes( tree, "ASK", "WHERE" );
    }

    @Test
    public void someDatasetClauses() throws Exception {
        Tree tree =
            parseTreeFor( "ASK FROM <http://a.com> FROM <http://b.com> WHERE { ?s ?p ?o . } " );
        assertLeafNodes( tree, "ASK", "FROM", "<http://a.com>", "FROM", "<http://b.com>",
            "WHERE" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnAskQuery() throws Exception {
        parseTreeFor( "ASK WHERE _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.askQuery().getTree();
    }
}
