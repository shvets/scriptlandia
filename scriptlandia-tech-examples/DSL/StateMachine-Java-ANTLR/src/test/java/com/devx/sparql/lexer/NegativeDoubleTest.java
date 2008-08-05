package com.devx.sparql.lexer;

import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.DoubleTest;

public class NegativeDoubleTest extends DoubleTest {
    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mNEGATIVE_DOUBLE();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.NEGATIVE_DOUBLE;
    }

    @Override
    protected String prefix() {
        return "-";
    }
}
