package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class GroupOrUnionGraphPatternTest extends GroupGraphPatternTest {
    @Test
    public void twoClauses() throws Exception {
        Tree tree = parseTreeFor( "{} UNION {}" );
        // TODO: Handle Union block
        assertNil( tree );
    }

    @Test
    public void threeClauses() throws Exception {
        Tree tree = parseTreeFor( "{} UNION {} UNION {}" );
        // TODO: Handle Union block
        assertNil( tree );
    }

    @Test( expected = RecognitionException.class )
    public void notAGroupOrUnionGraphPattern() throws Exception {
        parseTreeFor( "{} UNION _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.groupOrUnionGraphPattern().getTree();
    }
}
