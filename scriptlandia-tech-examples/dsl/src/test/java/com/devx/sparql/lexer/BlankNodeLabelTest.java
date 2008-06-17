package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class BlankNodeLabelTest extends LexerTest {
    @Test
    public void upperCaseLetters() throws Exception {
        for ( char ch = 'A'; ch <= 'Z'; ++ch )
            assertRecognized( "_:" + ch );
    }

    @Test
    public void lowerCaseLetters() throws Exception {
        for ( char ch = 'a'; ch <= 'z'; ++ch )
            assertRecognized( "_:" + ch );
    }

    @Test
    public void digits() throws Exception {
        for ( char ch = '0'; ch <= '9'; ++ch )
            assertRecognized( "_:" + ch );
    }

    @Test
    public void underscore() throws Exception {
        assertRecognized( "_:_" );
    }

    @Test
    public void undotted() throws Exception {
        assertRecognized( "_:abc" );
    }

    @Test
    public void singleDot() throws Exception {
        recognize( "_:a.b" );
    }

    @Test
    public void manyDots() throws Exception {
        recognize( "_:a_b0.c111_dd.e_" );
    }

    @Test( expected = RecognitionException.class )
    public void nonPrintableLocal() throws Exception {
        recognize( "_:..." );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mBLANK_NODE_LABEL();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.BLANK_NODE_LABEL;
    }
}
