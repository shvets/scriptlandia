package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class IntegerTest extends LexerTest {
    @Test
    public void uptoOneMillion() throws Exception {
        for ( int i = 0; i < 1e6; ++i )
            assertRecognized( prefix() + String.valueOf( i ) );
    }

    @Test( expected = RecognitionException.class )
    public void badDigits() throws Exception {
        assertRecognized( prefix() + "H128376" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mINTEGER();
    }
    
    @Override
    protected int expectedTokenType() {
        return SparqlLexer.INTEGER;
    }

    protected String prefix() {
        return "";
    }
}
