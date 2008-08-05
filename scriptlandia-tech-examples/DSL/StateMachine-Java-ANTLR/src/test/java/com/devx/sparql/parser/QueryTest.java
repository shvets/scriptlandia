package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class QueryTest extends ParserTest {
    @Test
    public void shouldRecognizeQueryAndCreateTree() throws RecognitionException {
        Tree tree = parseTreeFor( "SELECT ?s WHERE { ?s ?p ?o }" );

        assertTreeAndChildren( tree, SparqlParser.QUERY, "SELECT", "WHERE" );
    }

    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.query().getTree();
    }
}
