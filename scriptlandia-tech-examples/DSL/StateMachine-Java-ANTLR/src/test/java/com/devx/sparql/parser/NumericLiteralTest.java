package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class NumericLiteralTest extends ParserTest {
    @Test
    public void unsignedInteger() throws Exception {
        Tree tree = parseTreeFor( "1213476876" );
        assertLeafNode( tree, "1213476876" );
    }

    @Test
    public void unsignedDecimal() throws Exception {
        Tree tree = parseTreeFor( "   9283742.44 " );
        assertLeafNode( tree, "9283742.44" );
    }

    @Test
    public void unsignedDouble() throws Exception {
        Tree tree = parseTreeFor( " 3.14159E0  " );
        assertLeafNode( tree, "3.14159E0" );
    }

    @Test
    public void positiveInteger() throws Exception {
        Tree tree = parseTreeFor( "   +23452345 " );
        assertLeafNode( tree, "+23452345" );
    }

    @Test
    public void positiveDecimal() throws Exception {
        Tree tree = parseTreeFor( "   +436734.33 " );
        assertLeafNode( tree, "+436734.33" );
    }

    @Test
    public void positiveDouble() throws Exception {
        Tree tree = parseTreeFor( "    +4.56E-9" );
        assertLeafNode( tree, "+4.56E-9" );
    }

    @Test
    public void negativeInteger() throws Exception {
        Tree tree = parseTreeFor( "   -12313 " );
        assertLeafNode( tree, "-12313" );
    }

    @Test
    public void negativeDecimal() throws Exception {
        Tree tree = parseTreeFor( "   -12313.444 " );
        assertLeafNode( tree, "-12313.444" );
    }

    @Test
    public void negativeDouble() throws Exception {
        Tree tree = parseTreeFor( "-6.02E23 " );
        assertLeafNode( tree, "-6.02E23" );
    }

    @Test( expected = RecognitionException.class )
    public void notNumeric() throws Exception {
        parseTreeFor( "___" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.numericLiteral_return string = parser.numericLiteral();
        return (Tree) string.getTree();
    }
}
