package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class QuestionVarnameTest extends LexerTest {
    @Test
    public void testUpperCaseLetters() throws Exception {
        for ( char ch = 'A'; ch <= 'Z'; ++ch )
            assertRecognized( "?" + ch );
    }

    @Test
    public void testLowerCaseLetters() throws Exception {
        for ( char ch = 'a'; ch <= 'z'; ++ch )
            assertRecognized( "?" + ch );
    }

    @Test
    public void digits() throws Exception {
        for ( char ch = '0'; ch <= '9'; ++ch )
            assertRecognized( "?" + ch );
    }

    @Test
    public void underscore() throws Exception {
        assertRecognized( "?_" );
    }

    @Test
    public void varName() throws Exception {
        assertRecognized( "?abc0349\u00b7\u0300\u036f___\u203f\u2040" );
    }

    @Test( expected = RecognitionException.class )
    public void nonVarName() throws Exception {
        assertRecognized( "?.abcde" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mQUESTION_VARNAME();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.QUESTION_VARNAME;
    }
}
