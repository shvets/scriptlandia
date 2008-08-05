package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class TriplesBlockTest extends ParserTest {
    @Test
    public void shouldRecognizeTripleBlocks() throws RecognitionException {
        Tree tree = parseTreeFor( "?s1 ?p1 ?o1 . ?s2 ?p2 ?o2" );

        assertLeafNodes( tree, "TRIPLE", "TRIPLE" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.triplesBlock().getTree();
    }
}
