package com.devx.sparql.lexer;

import com.devx.sparql.SparqlLexer;

public class NegativeDecimalTest extends DecimalTest {
    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mNEGATIVE_DECIMAL();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.NEGATIVE_DECIMAL;
    }

    @Override
    protected String prefix() {
        return "-";
    }
}
