package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class ConstructTriplesTest extends TriplesSameSubjectTest {
    @Test
    public void oneTripleWithPeriod() throws Exception {
        Tree tree = parseTreeFor( "?aVariable ?verb ?object." );
        assertLeafNodes( tree, "TRIPLE", "." );
    }

    @Test
    public void twoTriplesWithOnePeriod() throws Exception {
        Tree tree =
            parseTreeFor( "?aVariable ?verb ?object. ?anotherVar ?verb2 ?object2" );
        assertLeafNodes( tree, "TRIPLE", ".", "TRIPLE" );
    }

    @Test
    public void twoTriplesWithTwoPeriods() throws Exception {
        Tree tree =
            parseTreeFor( "?aVariable ?verb ?object. ?anotherVar ?verb2 ?object2 ." );
        assertLeafNodes( tree, "TRIPLE", ".", "TRIPLE", "." );
    }

    @Test
    public void threeTriplesWithThreePeriods() throws Exception {
        Tree tree =
            parseTreeFor( "?aVariable ?verb ?object. ?anotherVar ?verb2 ?object2 . ?var3 ?p ?o ." );
        assertLeafNodes( tree, "TRIPLE", ".", "TRIPLE", ".", "TRIPLE", "." );
    }

    @Test( expected = RecognitionException.class )
    public void notAConstructTriples() throws Exception {
        parseTreeFor( "?s ?p _ ." );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.constructTriples().getTree();
    }
}
