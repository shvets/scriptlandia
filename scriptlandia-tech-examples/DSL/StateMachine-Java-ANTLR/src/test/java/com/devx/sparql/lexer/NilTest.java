package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.LexerTest;

public class NilTest extends LexerTest {
    @Test
    public void noWhitespace() throws Exception {
        assertRecognized( "()" );
    }

    @Test
    public void someWhitespace() throws Exception {
        assertRecognized( "(\r\n \t)" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnonymous() throws Exception {
        recognize( "___" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mNIL();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.NIL;
    }
}
