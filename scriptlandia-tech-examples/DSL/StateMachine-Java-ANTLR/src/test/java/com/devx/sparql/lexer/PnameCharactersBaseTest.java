package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.LexerTest;

public class PnameCharactersBaseTest extends LexerTest {
    @Test( expected = RecognitionException.class )
    public void nonPrintable() throws Exception {
        recognize( '\0' );
    }

    @Test
    public void asciiCapitals() throws Exception {
        recognizeRange( 'A', 'Z' );
    }

    @Test( expected = RecognitionException.class )
    public void asciiPuncutation() throws Exception {
        recognize( '^' );
    }

    @Test
    public void asciiLowercaseLetters() throws Exception {
        recognizeRange( 'a', 'z' );
    }

    @Test
    public void printableBlock1() throws Exception {
        recognizeRange( '\u00c0', '\u00d6' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock1And2() throws Exception {
        recognize( '\u00d7' );
    }

    @Test
    public void printableBlock2() throws Exception {
        recognizeRange( '\u00d8', '\u00f6' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock2And3() throws Exception {
        recognize( '\u00f7' );
    }

    @Test
    public void printableBlock3() throws Exception {
        recognizeRange( '\u00f8', '\u02ff' );
    }

    @Test
    public void printableBlock4() throws Exception {
        recognizeRange( '\u0370', '\u037d' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock4And5() throws Exception {
        recognize( '\u037e' );
    }

    @Test
    public void printableBlock5() throws Exception {
        recognizeRange( '\u037f', '\u1fff' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock5And6() throws Exception {
        recognize( '\u2000' );
    }

    @Test
    public void printableBlock6() throws Exception {
        recognizeRange( '\u200c', '\u200d' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock6And7() throws Exception {
        recognize( '\u2060' );
    }

    @Test
    public void printableBlock7() throws Exception {
        recognizeRange( '\u2070', '\u218f' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock7And8() throws Exception {
        recognize( '\u2a00' );
    }

    @Test
    public void printableBlock8() throws Exception {
        recognizeRange( '\u2c00', '\u2fef' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock8And9() throws Exception {
        recognize( '\u2ff0' );
    }

    @Test
    public void printableBlock9() throws Exception {
        recognizeRange( '\u3001', '\ud7ff' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock9And10() throws Exception {
        recognize( '\ue000' );
    }

    @Test
    public void printableBlock10() throws Exception {
        recognizeRange( '\uf900', '\ufdcf' );
    }

    @Test( expected = RecognitionException.class )
    public void betweenBlock10And11() throws Exception {
        recognize( '\ufde0' );
    }

    @Test
    public void printableBlock11() throws Exception {
        recognizeRange( '\ufdf0', '\ufffd' );
    }

    @Test( expected = RecognitionException.class )
    public void pastBlock11() throws Exception {
        recognize( '\uffff' );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPNAME_CHARS_BASE();
    }
}
