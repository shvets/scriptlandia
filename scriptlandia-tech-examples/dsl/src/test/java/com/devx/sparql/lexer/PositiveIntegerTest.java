package com.devx.sparql.lexer;

import com.devx.sparql.SparqlLexer;

public class PositiveIntegerTest extends IntegerTest {
    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPOSITIVE_INTEGER();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.POSITIVE_INTEGER;
    }

    @Override
    protected String prefix() {
        return "+";
    }
}
