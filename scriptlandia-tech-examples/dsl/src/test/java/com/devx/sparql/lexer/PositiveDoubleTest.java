package com.devx.sparql.lexer;

import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.DoubleTest;

public class PositiveDoubleTest extends DoubleTest {
    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPOSITIVE_DOUBLE();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.POSITIVE_DOUBLE;
    }

    @Override
    protected String prefix() {
        return "+";
    }
}
