package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class ArgListTest extends ParserTest {
    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.argList_return argList = parser.argList();
        return (Tree) argList.getTree();
    }

    @Test
    public void noArgs() throws Exception {
        assertLeafNode( parseTreeFor( "()" ), "()" );
    }

    // TODO: whitespace significant?
    @Test
    public void oneArg() throws Exception {
        Tree tree = parseTreeFor( "(" + Expressions.SIMPLE_EXPRESSION + ")" );
        assertLeafNodes( tree, "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void threeArgs() throws Exception {
        String text =
            "(" + Expressions.SIMPLE_EXPRESSION + ", " + Expressions.SIMPLE_EXPRESSION + ", "
                + Expressions.SIMPLE_EXPRESSION + ")";
        Tree tree = parseTreeFor( text );
        assertLeafNodes( tree, "(", Expressions.SIMPLE_EXPRESSION, ",", Expressions.SIMPLE_EXPRESSION,
            ",", Expressions.SIMPLE_EXPRESSION, ")" );
    }
}
