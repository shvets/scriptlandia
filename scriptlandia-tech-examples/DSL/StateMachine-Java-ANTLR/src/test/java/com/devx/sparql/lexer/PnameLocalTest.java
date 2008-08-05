package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class PnameLocalTest extends PnameCharactersPlusUnderscoreTest {
    @Test
    public void digits() throws Exception {
        recognizeRange( '0', '9' );
    }

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
        lexer.mPNAME_LOCAL();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.PNAME_LOCAL;
    }
}
