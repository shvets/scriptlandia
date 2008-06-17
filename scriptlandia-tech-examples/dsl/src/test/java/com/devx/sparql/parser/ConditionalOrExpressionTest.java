package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class ConditionalOrExpressionTest extends ConditionalAndExpressionTest {
    private String conditionalAnd;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.conditionalAnd = conditionalAnd();
    }

    @Test
    public void oneOr() throws Exception {
        Tree tree = parseTreeFor( conditionalAnd + "||" + conditionalAnd );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "=",
            unary(), "*", unary(), "+", unary(), "*", unary(), "&&", unary(), "*",
            unary(), "+", unary(), "*", unary(), "=", unary(), "*", unary(), "+",
            unary(), "*", unary(), "||", unary(), "*", unary(), "+", unary(), "*",
            unary(), "=", unary(), "*", unary(), "+", unary(), "*", unary(), "&&",
            unary(), "*", unary(), "+", unary(), "*", unary(), "=", unary(), "*",
            unary(), "+", unary(), "*", unary() );
    }

    @Test
    public void twoOrs() throws Exception {
        Tree tree = parseTreeFor( conditionalAnd + "||" + conditionalAnd + "  || "
            + conditionalAnd );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "=",
            unary(), "*", unary(), "+", unary(), "*", unary(), "&&", unary(), "*",
            unary(), "+", unary(), "*", unary(), "=", unary(), "*", unary(), "+",
            unary(), "*", unary(), "||", unary(), "*", unary(), "+", unary(), "*",
            unary(), "=", unary(), "*", unary(), "+", unary(), "*", unary(), "&&",
            unary(), "*", unary(), "+", unary(), "*", unary(), "=", unary(), "*",
            unary(), "+", unary(), "*", unary(), "||", unary(), "*", unary(), "+",
            unary(), "*", unary(), "=", unary(), "*", unary(), "+", unary(), "*",
            unary(), "&&", unary(), "*", unary(), "+", unary(), "*", unary(), "=",
            unary(), "*", unary(), "+", unary(), "*", unary() );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.conditionalOrExpression().getTree();
    }
}
