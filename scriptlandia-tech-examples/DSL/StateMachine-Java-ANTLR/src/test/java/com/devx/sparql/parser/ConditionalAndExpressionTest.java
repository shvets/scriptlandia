package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class ConditionalAndExpressionTest extends ValueLogicalTest {
    private String valueLogical;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.valueLogical = valueLogical();
    }

    @Test
    public void oneAnd() throws Exception {
        Tree tree = parseTreeFor( valueLogical + "&&" + valueLogical );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "=",
            unary(), "*", unary(), "+", unary(), "*", unary(), "&&", unary(), "*",
            unary(), "+", unary(), "*", unary(), "=", unary(), "*", unary(), "+",
            unary(), "*", unary() );
    }

    @Test
    public void threeAnds() throws Exception {
        Tree tree = parseTreeFor( valueLogical + "&&" + valueLogical + "  &&"
            + valueLogical + "&&" + valueLogical );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "=",
            unary(), "*", unary(), "+", unary(), "*", unary(), "&&", unary(), "*",
            unary(), "+", unary(), "*", unary(), "=", unary(), "*", unary(), "+",
            unary(), "*", unary(), "&&", unary(), "*", unary(), "+", unary(), "*",
            unary(), "=", unary(), "*", unary(), "+", unary(), "*", unary(), "&&",
            unary(), "*", unary(), "+", unary(), "*", unary(), "=", unary(), "*",
            unary(), "+", unary(), "*", unary() );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.conditionalAndExpression().getTree();
    }

    protected String conditionalAnd() {
        return valueLogical() + " && " + valueLogical();
    }
}
