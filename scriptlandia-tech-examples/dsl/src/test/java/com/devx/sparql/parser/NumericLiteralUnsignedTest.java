package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class NumericLiteralUnsignedTest extends ParserTest {
    @Test
    public void literalInteger() throws Exception {
        Tree tree = parseTreeFor( "1213476876" );
        assertLeafNode( tree, "1213476876" );
    }

    @Test
    public void literalDecimal() throws Exception {
        Tree tree = parseTreeFor( "   9283742.44 " );
        assertLeafNode( tree, "9283742.44" );
    }

    @Test
    public void literalDouble() throws Exception {
        Tree tree = parseTreeFor( " 3.14159E0  " );
        assertLeafNode( tree, "3.14159E0" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnUnsignedNumber() throws Exception {
        parseTreeFor( "+3.14159" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.numericLiteralUnsigned_return string =
            parser.numericLiteralUnsigned();
        return (Tree) string.getTree();
    }
}
