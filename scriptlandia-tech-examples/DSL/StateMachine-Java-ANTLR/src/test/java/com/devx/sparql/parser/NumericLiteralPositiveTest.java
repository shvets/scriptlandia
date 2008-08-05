package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class NumericLiteralPositiveTest extends ParserTest {
    @Test
    public void literalInteger() throws Exception {
        Tree tree = parseTreeFor( "   +23452345 " );
        assertLeafNode( tree, "+23452345" );
    }

    @Test
    public void literalDecimal() throws Exception {
        Tree tree = parseTreeFor( "   +436734.33 " );
        assertLeafNode( tree, "+436734.33" );
    }

    @Test
    public void literalDouble() throws Exception {
        Tree tree = parseTreeFor( "    +4.56E-9" );
        assertLeafNode( tree, "+4.56E-9" );
    }

    @Test( expected = RecognitionException.class )
    public void notAPositiveNumber() throws Exception {
        parseTreeFor( "-3.14159" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.numericLiteralPositive_return string =
            parser.numericLiteralPositive();
        return (Tree) string.getTree();
    }
}
