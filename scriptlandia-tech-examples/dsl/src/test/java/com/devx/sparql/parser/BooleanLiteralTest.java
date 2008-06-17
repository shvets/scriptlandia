package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class BooleanLiteralTest extends ParserTest {
    @Test
    public void literalTrue() throws Exception {
        Tree tree = parseTreeFor( "   true   " );
        assertLeafNode( tree, "true" );
    }

    @Test
    public void literalFalse() throws Exception {
        Tree tree = parseTreeFor( " false" );
        assertLeafNode( tree, "false" );
    }

    @Test( expected = RecognitionException.class )
    public void notABoolean() throws Exception {
        parseTreeFor( "  maybe  " );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.booleanLiteral_return string = parser.booleanLiteral();
        return (Tree) string.getTree();
    }
}
