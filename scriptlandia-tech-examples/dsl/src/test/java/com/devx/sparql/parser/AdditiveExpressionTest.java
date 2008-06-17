package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class AdditiveExpressionTest extends MultiplicativeExpressionTest {
    private String multiplicative;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.multiplicative = multiplicative();
    }

    @Test
    public void oneAddition() throws Exception {
        Tree tree = parseTreeFor( multiplicative + " + " + multiplicative );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary() );
    }

    @Test
    public void oneSubtraction() throws Exception {
        Tree tree = parseTreeFor( multiplicative + "  - " + multiplicative );
        assertLeafNodes( tree, unary(), "*", unary(), "-", unary(), "*", unary() );
    }

    @Test
    public void withNumericLiteralPositive() throws Exception {
        Tree tree = parseTreeFor( multiplicative + "  +239203" );
        assertLeafNodes( tree, unary(), "*", unary(), "+239203" );
    }

    @Test
    public void withNumericLiteralNegative() throws Exception {
        Tree tree = parseTreeFor( multiplicative + " -444" );
        assertLeafNodes( tree, unary(), "*", unary(), "-444" );
    }

    @Test
    public void mixedBag() throws Exception {
        Tree tree = parseTreeFor( multiplicative + " + " + multiplicative + " - "
            + multiplicative + "  -2323 +3444" );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "-",
            unary(), "*", unary(), "-2323", "+3444" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnAdditiveExpression() throws Exception {
        parseTreeFor( multiplicative + " */ " + multiplicative );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.additiveExpression().getTree();
    }

    protected String additive() {
        return multiplicative() + " + " + multiplicative();
    }
}
