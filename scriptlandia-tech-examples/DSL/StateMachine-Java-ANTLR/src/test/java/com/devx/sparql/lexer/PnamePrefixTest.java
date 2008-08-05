package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class PnamePrefixTest extends PnameCharactersBaseTest {
    @Test
    public void undotted() throws Exception {
        recognize( "abc" );
    }

    @Test
    public void singleDot() throws Exception {
        recognize( "a.b" );
    }

    @Test
    public void manyDots() throws Exception {
        recognize( "a_b0.c111_dd.e_" );
    }

    @Test( expected = RecognitionException.class )
    public void nonPrintableLocal() throws Exception {
        recognize( "..." );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPNAME_PREFIX();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.PNAME_PREFIX;
    }
}
