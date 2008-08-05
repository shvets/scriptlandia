package com.devx.sparql.parser;

import static com.devx.sparql.parser.Expressions.SIMPLE_EXPRESSION;
import com.devx.sparql.SparqlParser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Before;
import org.junit.Test;

public class PrimaryExpressionTest extends ParserTest {
    private String primary;

    @Before
    public void setUp() {
        this.primary = primary();
    }

    @Test
    public void bracketted() throws Exception {
        Tree tree = parseTreeFor( "(" + primary + ")" );
        assertLeafNodes( tree, "(", primary, ")" );
    }

    @Test
    public void builtInCall() throws Exception {
        Tree tree = parseTreeFor( "isIRI   (" + primary + ")" );
        assertLeafNodes( tree, "isIRI", "(", primary, ")" );
    }

    @Test
    public void iriReference() throws Exception {
        Tree tree = parseTreeFor( "<http://boo.com>" );
        assertLeafNode( tree, "<http://boo.com>" );
    }

    @Test
    public void function() throws Exception {
        Tree tree = parseTreeFor( "prefix:name(" + primary + "  )  " );
        assertLeafNodes( tree, "prefix:name", "(", primary, ")" );
    }

    @Test
    public void rdfLiteral() throws Exception {
        Tree tree = parseTreeFor( "'hooda-booda'@en" );
        assertLeafNodes( tree, "'hooda-booda'", "@en" );
    }

    @Test
    public void numericLiteral() throws Exception {
        Tree tree = parseTreeFor( "-2" );
        assertLeafNode( tree, "-2" );
    }

    @Test
    public void booleanLiteral() throws Exception {
        Tree tree = parseTreeFor( "true" );
        assertLeafNode( tree, "true" );
    }

    @Test( expected = RecognitionException.class )
    public void notAPrimaryExpression() throws Exception {
        parseTreeFor( "!(@&#" );
    }

    @Test
    public void variable() throws Exception {
        Tree tree = parseTreeFor( "?aVariable" );
        assertLeafNode( tree, "?aVariable" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.primaryExpression().getTree();
    }

    protected String primary() {
        return SIMPLE_EXPRESSION;
    }
}
