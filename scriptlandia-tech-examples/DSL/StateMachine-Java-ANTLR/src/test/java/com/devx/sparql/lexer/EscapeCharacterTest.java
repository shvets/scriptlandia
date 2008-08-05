package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.LexerTest;

public class EscapeCharacterTest extends LexerTest {
    @Test
    public void tab() throws Exception {
        assertRecognized( "\\t" );
    }

    @Test
    public void b() throws Exception {
        assertRecognized( "\\b" );
    }

    @Test
    public void newLine() throws Exception {
        assertRecognized( "\\n" );
    }

    @Test
    public void carriageReturn() throws Exception {
        assertRecognized( "\\r" );
    }

    @Test
    public void formFeed() throws Exception {
        assertRecognized( "\\f" );
    }

    @Test
    public void backSlash() throws Exception {
        assertRecognized( "\\\\" );
    }

    @Test
    public void doubleQuote() throws Exception {
        assertRecognized( "\\\"" );
    }

    @Test
    public void singleQuote() throws Exception {
        assertRecognized( "\\'" );
    }

    @Test( expected = RecognitionException.class )
    public void nonEscapeCharacter() throws Exception {
        recognize( "\\." );
    }
 
    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mESCAPE_CHARACTER();
    }
    
    @Override
    protected int expectedTokenType() {
        return SparqlLexer.ESCAPE_CHARACTER;
    }
}
