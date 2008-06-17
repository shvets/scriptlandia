package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class GraphPatternNotTriplesTest extends GroupOrUnionGraphPatternTest {
    @Test
    public void optionalGraphPattern() throws Exception {
        Tree tree = parseTreeFor( "   OPTIONAL    { } " );
        // TODO: Handle optional block
        assertNil( tree );
    }

    @Test
    public void graphGraphPattern() throws Exception {
        Tree tree = parseTreeFor( "GRAPH ?var {}" );
        // TODO: Handle optional block
        assertNil( tree );
    }

    @Test( expected = RecognitionException.class )
    public void notAGraphPatternNotTriples() throws Exception {
        parseTreeFor( "OPTIONAL { _ } " );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.graphPatternNotTriples().getTree();
    }
}
