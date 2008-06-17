package com.devx.sparql.lexer;

import com.devx.sparql.SparqlLexer;

public class NegativeIntegerTest extends IntegerTest {
    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mNEGATIVE_INTEGER();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.NEGATIVE_INTEGER;
    }

    @Override
    protected String prefix() {
        return "-";
    }
}
