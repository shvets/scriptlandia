package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class BuiltInCallTest extends RegexExpressionTest {
    @Test
    public void str() throws Exception {
        Tree tree = parseTreeFor( "STR (" + Expressions.SIMPLE_EXPRESSION + "   )" );
        assertLeafNodes( tree, "STR", "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void lang() throws Exception {
        Tree tree = parseTreeFor( "LANG     (" + Expressions.SIMPLE_EXPRESSION + "   )" );
        assertLeafNodes( tree, "LANG", "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void langMatches() throws Exception {
        Tree tree =
            parseTreeFor( "LANGMATCHES (" + Expressions.SIMPLE_EXPRESSION + "  , " + Expressions.SIMPLE_EXPRESSION
                + " )" );
        assertLeafNodes( tree, "LANGMATCHES", "(", Expressions.SIMPLE_EXPRESSION, ",",
                Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void dataType() throws Exception {
        Tree tree = parseTreeFor( "DATATYPE (" + Expressions.SIMPLE_EXPRESSION + "   )" );
        assertLeafNodes( tree, "DATATYPE", "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void bound() throws Exception {
        Tree tree = parseTreeFor( "BOUND (?aVar ) " );
        assertLeafNodes( tree, "BOUND", "(", "?aVar", ")" );
    }

    @Test
    public void sameTerm() throws Exception {
        Tree tree =
            parseTreeFor( "sameTerm(" + Expressions.SIMPLE_EXPRESSION + "  , " + Expressions.SIMPLE_EXPRESSION
                + " )" );
        assertLeafNodes( tree, "sameTerm", "(", Expressions.SIMPLE_EXPRESSION, ",", Expressions.SIMPLE_EXPRESSION,
            ")" );
    }

    @Test
    public void isIRI() throws Exception {
        Tree tree = parseTreeFor( "isIRI   (" + Expressions.SIMPLE_EXPRESSION + " )  " );
        assertLeafNodes( tree, "isIRI", "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void isURI() throws Exception {
        Tree tree = parseTreeFor( "isURI(" + Expressions.SIMPLE_EXPRESSION + " )  " );
        assertLeafNodes( tree, "isURI", "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void isBlank() throws Exception {
        Tree tree = parseTreeFor( "isBLANK    (" + Expressions.SIMPLE_EXPRESSION + ")" );
        assertLeafNodes( tree, "isBLANK", "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void isLiteral() throws Exception {
        Tree tree = parseTreeFor( "isLITERAL(" + Expressions.SIMPLE_EXPRESSION + ")" );
        assertLeafNodes( tree, "isLITERAL", "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test( expected = RecognitionException.class )
    public void notABuiltInCall() throws Exception {
        parseTreeFor( "joeBlow(" + Expressions.SIMPLE_EXPRESSION + ")" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.builtInCall_return builtInCall = parser.builtInCall();
        return (Tree) builtInCall.getTree();
    }
}
