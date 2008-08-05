package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class PnameNamespaceTest extends LexerTest {
    @Test
    public void upperCaseLetters() throws Exception {
        for ( char ch = 'A'; ch <= 'Z'; ++ch )
            assertRecognized( String.valueOf( ch ) + ':' );
    }

    @Test
    public void lowerCaseLetters() throws Exception {
        for ( char ch = 'a'; ch <= 'z'; ++ch )
            assertRecognized( String.valueOf( ch ) + ':' );
    }

    @Test
    public void undotted() throws Exception {
        assertRecognized( "abc:" );
    }

    @Test
    public void singleDot() throws Exception {
        recognize( "a.b:" );
    }

    @Test
    public void manyDots() throws Exception {
        recognize( "a_b0.c111_dd.e_:" );
    }

    @Test( expected = RecognitionException.class )
    public void nonPrintableLocal() throws Exception {
        recognize( "_...:" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPNAME_NAMESPACE();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.PNAME_NAMESPACE;
    }
}
