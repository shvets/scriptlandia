package com.devx.sparql.parser;

import static com.devx.sparql.parser.Expressions.*;
import com.devx.sparql.SparqlParser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class RegexExpressionTest extends ParserTest {
    @Test
    public void twoArgs() throws Exception {
        Tree tree =
            parseTreeFor( "REGEX (" + SIMPLE_EXPRESSION + ", "
                + SIMPLE_EXPRESSION + " )" );
        assertLeafNodes( tree, "REGEX", "(", SIMPLE_EXPRESSION, ",",
            SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void threeArgs() throws Exception {
        Tree tree =
            parseTreeFor( "REGEX (" + SIMPLE_EXPRESSION + ", "
                + SIMPLE_EXPRESSION + " , " + SIMPLE_EXPRESSION + " )" );
        assertLeafNodes( tree, "REGEX", "(", SIMPLE_EXPRESSION, ",",
            SIMPLE_EXPRESSION, ",", SIMPLE_EXPRESSION, ")" );
    }

    @Test( expected = RecognitionException.class )
    public void notARegexExpression() throws Exception {
        parseTreeFor( "REGEX(" + SIMPLE_EXPRESSION + ")" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.regexExpression_return regexExpression = parser.regexExpression();
        return (Tree) regexExpression.getTree();
    }
}
