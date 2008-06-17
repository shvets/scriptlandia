package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Ignore;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class BlankNodeTest extends ParserTest {
    @Test
    public void blankNodeLabel() throws Exception {
        Tree tree = parseTreeFor( "    _:abc   " );
        assertLeafNode( tree, "_:abc" );
    }

    @Test
    public void anonymous() throws Exception {
        Tree tree = parseTreeFor( "     []    " );
        assertLeafNode( tree, "[]" );
    }

    @Test
    @Ignore( "figure out why the intervening whitespace screws up the recognition" )
    public void anonymousWithInterveningWhitespace() throws Exception {
        Tree tree = parseTreeFor( "     [ \t\n\r]    " );
        assertLeafNode( tree, "[ \t\n\r]" );
    }

    @Test( expected = RecognitionException.class )
    public void notABlankNode() throws Exception {
        parseTreeFor( "     asdasd    " );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.blankNode_return blankNode = parser.blankNode();
        return (Tree) blankNode.getTree();
    }
}
