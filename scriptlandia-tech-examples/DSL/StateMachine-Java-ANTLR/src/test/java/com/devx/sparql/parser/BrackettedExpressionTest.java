package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class BrackettedExpressionTest extends ParserTest {
    @Test
    public void testBracketted() throws Exception {
        Tree tree = parseTreeFor( "  (" + Expressions.SIMPLE_EXPRESSION + "  )" );
        assertLeafNodes( tree, "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test( expected = RecognitionException.class )
    public void testNotABrackettedExpression() throws Exception {
        parseTreeFor( "(2, 3)" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.brackettedExpression_return bracketted =
            parser.brackettedExpression();
        return (Tree) bracketted.getTree();
    }
}
