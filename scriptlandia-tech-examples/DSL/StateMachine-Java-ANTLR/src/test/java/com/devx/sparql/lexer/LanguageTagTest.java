package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class LanguageTagTest extends LexerTest {
    @Test
    public void singleLowerCaseLetters() throws Exception {
        for ( char ch = 'a'; ch <= 'z'; ++ch )
            assertRecognized( "@" + ch );
    }

    @Test
    public void singleUpperCaseLetters() throws Exception {
        for ( char ch = 'A'; ch <= 'Z'; ++ch )
            assertRecognized( "@" + ch );
    }

    @Test
    public void multipleLettersMixedCase() throws Exception {
        assertRecognized( "@askdjhKAJSkjahksbncdSDASD" );
    }

    @Test
    public void separatingHyphens() throws Exception {
        assertRecognized( "@a-b1-2c-d3" );
    }

    @Test( expected = RecognitionException.class )
    public void badLeadingCharacter() throws Exception {
        recognize( "@8askdjh-wedwedv44" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mLANGUAGE_TAG();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.LANGUAGE_TAG;
    }
}
