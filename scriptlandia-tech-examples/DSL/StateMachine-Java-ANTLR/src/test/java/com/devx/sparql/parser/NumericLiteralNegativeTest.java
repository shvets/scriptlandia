package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class NumericLiteralNegativeTest extends ParserTest {
    @Test
    public void literalInteger() throws Exception {
        Tree tree = parseTreeFor( "   -12313 " );
        assertLeafNode( tree, "-12313" );
    }

    @Test
    public void literalDecimal() throws Exception {
        Tree tree = parseTreeFor( "   -12313.444 " );
        assertLeafNode( tree, "-12313.444" );
    }

    @Test
    public void literalDouble() throws Exception {
        Tree tree = parseTreeFor( "-6.02E23 " );
        assertLeafNode( tree, "-6.02E23" );
    }

    @Test( expected = RecognitionException.class )
    public void notANegativeNumber() throws Exception {
        parseTreeFor( "3.14159" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.numericLiteralNegative_return string =
            parser.numericLiteralNegative();
        return (Tree) string.getTree();
    }
}
