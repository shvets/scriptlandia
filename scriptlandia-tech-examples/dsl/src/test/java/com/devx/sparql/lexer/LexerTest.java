package com.devx.sparql.lexer;

import static org.junit.Assert.*;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Token;
import com.devx.sparql.SparqlLexer;

public abstract class LexerTest {
    protected void assertRangeOfCharactersRecognized( char low, char high )
        throws Exception {
    
        for ( char ch = low; ch <= high; ++ch )
            assertRecognized( ch );
    }

    protected void recognizeRange( char low, char high ) throws Exception {
        for ( char ch = low; ch <= high; ++ch )
            recognize( ch );
    }

    protected void assertRecognized( char ch ) throws Exception {
        assertRecognized( String.valueOf( ch ) );
    }

    protected Token recognize( char ch ) throws Exception {
        return recognize( String.valueOf( ch ) );
    }

    protected void assertRecognized( String text ) throws Exception {
        Token token = recognize( text );
        assertEquals( text, token.getText() );
        
        if ( expectedTokenType() != Integer.MIN_VALUE )
            assertEquals( expectedTokenType(), token.getType() );
    }

    protected Token recognize( String text ) throws Exception {
        SparqlLexer lexer = lexerOver( text );
        fireLexerRule( lexer );
        lexer = lexerOver( text );
        return lexer.nextToken();
    }

    private SparqlLexer lexerOver( String text ) {
        SparqlLexer lexer = new SparqlLexer();
        lexer.setCharStream( new ANTLRStringStream( text ) );
        return lexer;
    }

    protected abstract void fireLexerRule( SparqlLexer lexer ) throws Exception;

    protected int expectedTokenType() {
        return Integer.MIN_VALUE;
    }
}
