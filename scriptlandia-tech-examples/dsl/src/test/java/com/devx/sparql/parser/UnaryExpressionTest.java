package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class UnaryExpressionTest extends PrimaryExpressionTest {
    private String primary;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.primary = primary();
    }

    @Test
    public void negated() throws Exception {
        Tree tree = parseTreeFor( "!" + primary );
        assertLeafNodes( tree, "!", primary );
    }

    @Test
    public void positive() throws Exception {
        Tree tree = parseTreeFor( "+" + primary );
        assertLeafNode( tree, "+" + primary );
    }

    @Test
    public void negative() throws Exception {
        Tree tree = parseTreeFor( "-" + primary );
        assertLeafNode( tree, "-" + primary );
    }

    @Test( expected = RecognitionException.class )
    public void notAUnaryExpression() throws Exception {
        parseTreeFor( "^" + primary );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.unaryExpression().getTree();
    }

    protected String unary() {
        return "-" + primary();
    }
}
