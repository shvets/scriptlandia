package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class GraphGraphPatternTest extends ParserTest {
    @Test
    public void graphGraphPattern() throws Exception {
        Tree tree = parseTreeFor( "GRAPH ?var {}" );
        assertLeafNodes( tree, "GRAPH", "?var" );
    }

    @Test( expected = RecognitionException.class )
    public void notAGraphGraphPattern() throws Exception {
        parseTreeFor( "GRAPH _ {}" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.graphGraphPattern().getTree();
    }
}
