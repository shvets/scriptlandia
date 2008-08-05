package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class RelationalExpressionTest extends NumericExpressionTest {
    private String numeric;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.numeric = numeric();
    }

    @Test
    public void equal() throws Exception {
        Tree tree = parseTreeFor( numeric + "=" + numeric );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "=",
            unary(), "*", unary(), "+", unary(), "*", unary() );
    }

    @Test
    public void notEqual() throws Exception {
        Tree tree = parseTreeFor( numeric + "  !=  " + numeric );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "!=",
            unary(), "*", unary(), "+", unary(), "*", unary() );
    }

    @Test
    public void lessThan() throws Exception {
        Tree tree = parseTreeFor( numeric + "<   " + numeric );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "<",
            unary(), "*", unary(), "+", unary(), "*", unary() );
    }

    @Test
    public void lessThanOrEqual() throws Exception {
        Tree tree = parseTreeFor( numeric + " <=  " + numeric );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), "<=",
            unary(), "*", unary(), "+", unary(), "*", unary() );
    }

    @Test
    public void greaterThan() throws Exception {
        Tree tree = parseTreeFor( numeric + "    >" + numeric );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), ">",
            unary(), "*", unary(), "+", unary(), "*", unary() );
    }

    @Test
    public void greaterThanOrEqual() throws Exception {
        Tree tree = parseTreeFor( numeric + "    >=" + numeric );
        assertLeafNodes( tree, unary(), "*", unary(), "+", unary(), "*", unary(), ">=",
            unary(), "*", unary(), "+", unary(), "*", unary() );
    }

    @Test( expected = RecognitionException.class )
    public void notARelationalExpression() throws Exception {
        parseTreeFor( numeric + " == " + numeric );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.relationalExpression().getTree();
    }

    protected String relational() {
        return numeric() + " = " + numeric();
    }
}
