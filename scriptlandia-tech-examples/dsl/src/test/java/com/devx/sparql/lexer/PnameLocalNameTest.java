package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class PnameLocalNameTest extends LexerTest {
    @Test
    public void upperCaseLetters() throws Exception {
        for ( char ch = 'A'; ch <= 'Z'; ++ch )
            assertRecognized( String.valueOf( ch ) + ":ASKDJHASD" );
    }

    @Test
    public void lowerCaseLetters() throws Exception {
        for ( char ch = 'a'; ch <= 'z'; ++ch )
            assertRecognized( String.valueOf( ch ) + ":ksajdhficur" );
    }

    @Test
    public void undotted() throws Exception {
        assertRecognized( "abc:askdjfhas" );
    }

    @Test
    public void singleDot() throws Exception {
        recognize( "a.b:asdkfi89asdf" );
    }

    @Test
    public void manyDots() throws Exception {
        recognize( "a_b0.c111_dd.e_:ASD_asd_89__" );
    }

    @Test( expected = RecognitionException.class )
    public void nonPrintableLocal() throws Exception {
        recognize( "...:ALSKDJA" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPNAME_LOCAL_NAME();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.PNAME_LOCAL_NAME;
    }
}
