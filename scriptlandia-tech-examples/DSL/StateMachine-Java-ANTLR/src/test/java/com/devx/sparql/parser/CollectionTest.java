package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class CollectionTest extends ParserTest {
    @Test
    public void oneNode() throws Exception {
        Tree tree = parseTreeFor( "(?someVar )" );
        assertLeafNodes( tree, "(", "?someVar", ")" );
    }

    @Test
    public void threeNodes() throws Exception {
        Tree tree = parseTreeFor( "(?s ?p ?o)" );
        assertLeafNodes( tree, "(", "?s", "?p", "?o", ")" );
    }

    @Test( expected = RecognitionException.class )
    public void notACollection() throws Exception {
        parseTreeFor( "_" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.collection().getTree();
    }
}
