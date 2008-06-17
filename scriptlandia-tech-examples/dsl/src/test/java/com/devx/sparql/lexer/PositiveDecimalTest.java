package com.devx.sparql.lexer;

import com.devx.sparql.SparqlLexer;

public class PositiveDecimalTest extends DecimalTest {
    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPOSITIVE_DECIMAL();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.POSITIVE_DECIMAL;
    }

    @Override
    protected String prefix() {
        return "+";
    }
}
