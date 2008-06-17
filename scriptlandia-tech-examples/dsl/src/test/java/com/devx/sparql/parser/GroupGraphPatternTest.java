package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class GroupGraphPatternTest extends ParserTest {
    @Test
    public void empty() throws Exception {
        Tree tree = parseTreeFor( "{}" );
        assertNil( tree );
    }

    @Test
    public void oneTriple() throws Exception {
        Tree tree = parseTreeFor( "{ ?s ?p ?o }" );
        assertTreeAndChildren( tree, SparqlParser.TRIPLE, "?s", "?p", "?o" );
    }

    @Test
    public void oneTripleWithPeriod() throws Exception {
        Tree tree = parseTreeFor( "{ ?s ?p ?o. }" );
        assertTreeAndChildren( tree, SparqlParser.TRIPLE, "?s", "?p", "?o" );
    }

    @Test
    public void oneTripleWithOptional() throws Exception {
        Tree tree = parseTreeFor( "{ ?s ?p ?o. OPTIONAL { ?s1 ?p1 ?o1 } }" );
        // TODO: Handle optional block
        assertTreeAndChildren( tree, SparqlParser.TRIPLE, "?s", "?p", "?o" );
    }

    @Test
    public void oneTripleWithGraph() throws Exception {
        Tree tree = parseTreeFor( "{ ?s ?p ?o. GRAPH a:b { ?s1 ?p1 ?o1 } }" );
        // TODO: Handle graph block
        assertTreeAndChildren( tree, SparqlParser.TRIPLE, "?s", "?p", "?o" );
    }

    // TODO: can probably add some more tests here.

    @Test( expected = RecognitionException.class )
    public void notAGroupGraphPattern() throws Exception {
        parseTreeFor( " {  _    }" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.groupGraphPattern().getTree();
    }
}
