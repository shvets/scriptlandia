package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class DecimalTest extends LexerTest {
    @Test
    public void zeroToOneThousandByHundredths() throws Exception {
        for ( double d = 0.0; d <= 1000.0; d += 0.01 )
            assertRecognized( prefix() + String.valueOf( d ) );
    }

    @Test
    public void zeroToOneByThousandthsWithLeadingDecimalPoint() throws Exception {
        for ( int i = 0; i < 1000; ++i )
            assertRecognized( prefix() + "." + i );
    }

    @Test( expected = RecognitionException.class )
    public void badLeadingDigit() throws Exception {
        assertRecognized( prefix() + "A20034.3434" );
    }

    @Test( expected = RecognitionException.class )
    public void badDigitAfterDecimalPoint() throws Exception {
        assertRecognized( prefix() + ".CAFE0009" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mDECIMAL();
    }
    
    protected String prefix() {
        return "";
    }
}
