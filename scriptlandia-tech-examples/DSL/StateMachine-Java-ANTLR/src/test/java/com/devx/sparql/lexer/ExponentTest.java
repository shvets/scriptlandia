package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.LexerTest;

public class ExponentTest extends LexerTest {
    @Test
    public void singleDigits() throws Exception {
        for ( char ch = '0'; ch <= '9'; ++ch ) {
            assertRecognized( "e" + ch );
            assertRecognized( "E" + ch );
        }
    }

    @Test
    public void singleDigitPositive() throws Exception {
        for ( char ch = '0'; ch <= '9'; ++ch ) {
            assertRecognized( "e+" + ch );
            assertRecognized( "E+" + ch );
        }
    }

    @Test
    public void singleDigitNegative() throws Exception {
        for ( char ch = '0'; ch <= '9'; ++ch ) {
            assertRecognized( "e-" + ch );
            assertRecognized( "E-" + ch );
        }
    }

    @Test
    public void multiDigitPositive() throws Exception {
        assertRecognized( "e+0928310" );
        assertRecognized( "E+348572983475" );
    }

    @Test
    public void multiDigitNegative() throws Exception {
        assertRecognized( "e-23947813248" );
        assertRecognized( "E-53794581" );
    }

    @Test( expected = RecognitionException.class )
    public void noExponentIndicator() throws Exception {
        recognize( "-12312" );
    }

    @Test( expected = RecognitionException.class )
    public void badDigits() throws Exception {
        recognize( "E+FF02" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mEXPONENT();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.EXPONENT;
    }
}
