package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.LexerTest;

public class WhitespaceTest extends LexerTest {
    @Test
    public void tab() throws Exception {
        assertRecognized( '\t' );
    }

    @Test
    public void space() throws Exception {
        assertRecognized( ' ' );
    }

    @Test
    public void newLine() throws Exception {
        assertRecognized( '\n' );
    }

    @Test
    public void carriageReturn() throws Exception {
        assertRecognized( '\r' );
    }

    @Test( expected = RecognitionException.class )
    public void nonSpace() throws Exception {
        recognize( '4' );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mWHITESPACE();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.WHITESPACE;
    }
}
