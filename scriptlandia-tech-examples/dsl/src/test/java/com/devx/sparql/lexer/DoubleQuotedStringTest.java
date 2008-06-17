package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.LexerTest;

public class DoubleQuotedStringTest extends LexerTest {
    @Test
    public void empty() throws Exception {
        assertRecognized( "\"\"" );
    }

    @Test
    public void nonEmpty() throws Exception {
        assertRecognized( "\"here is a double-quoted string\"" );
    }

    @Test( expected = RecognitionException.class )
    public void illegalCharacters() throws Exception {
        recognize( "\"this is\nillegal\"" );    
    }

    @Test( expected = RecognitionException.class )
    public void missingLeadingQuote() throws Exception {
        recognize( "this is illegal\"" );    
    }

    @Test( expected = RecognitionException.class )
    public void missingTrailingQuote() throws Exception {
        recognize( "\"this is illegal" );    
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mDOUBLE_QUOTED_STRING();
    }
    
    @Override
    protected int expectedTokenType() {
        return SparqlLexer.DOUBLE_QUOTED_STRING;
    }
}
