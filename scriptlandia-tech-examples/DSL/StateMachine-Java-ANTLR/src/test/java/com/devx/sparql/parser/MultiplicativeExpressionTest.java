package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class MultiplicativeExpressionTest extends UnaryExpressionTest {
    private String unary;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.unary = unary();
    }

    @Test
    public void oneMultiplication() throws Exception {
        Tree tree = parseTreeFor( unary + " * " + unary );
        assertLeafNodes( tree, unary, "*", unary );
    }

    @Test
    public void oneDivision() throws Exception {
        Tree tree = parseTreeFor( unary + "    /" + unary );
        assertLeafNodes( tree, unary, "/", unary );
    }

    @Test
    public void expressionCombination() throws Exception {
        Tree tree = parseTreeFor( unary + " * " + unary + '/' + unary + '/' + unary
            + "   *" + unary );
        assertLeafNodes( tree, unary, "*", unary, "/", unary, "/", unary, "*", unary );
    }

    @Test( expected = RecognitionException.class )
    public void notAMultiplicativeExpression() throws Exception {
        parseTreeFor( unary + "//" + unary );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.multiplicativeExpression().getTree();
    }

    protected String multiplicative() {
        return unary() + " * " + unary();
    }
}
