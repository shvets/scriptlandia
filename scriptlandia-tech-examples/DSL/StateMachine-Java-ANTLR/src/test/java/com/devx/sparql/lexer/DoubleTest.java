package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.LexerTest;

public class DoubleTest extends LexerTest {
    @Test
    public void zeroToOneHundredByHundredths() throws Exception {
        for ( double d = 0.0; d <= 100.0; d += 0.01 ) {
            for ( int exponent = -100; exponent < 11; ++exponent ) {
                assertRecognized( prefix() + String.valueOf( d ) + "e" + exponent );
                assertRecognized( prefix() + String.valueOf( d ) + "E" + exponent );
            }
        }
    }

    @Test
    public void zeroToOneByThousandthsWithLeadingDecimalPoint() throws Exception {
        for ( int i = 0; i < 1000; ++i ) {
            for ( int exponent = -50; exponent < 29; ++exponent ) {
                assertRecognized( prefix() + "." + i + "e" + exponent );
                assertRecognized( prefix() + "." + i + "E" + exponent );
            }
        }
    }

    @Test
    public void zeroToOneByThousandthsWithNoDecimalPoint() throws Exception {
        for ( int i = 0; i < 1000; ++i ) {
            for ( int exponent = -50; exponent < 29; ++exponent ) {
                assertRecognized( prefix() + i + "e" + exponent );
                assertRecognized( prefix() + i + "E" + exponent );
            }
        }
    }

    @Test( expected = RecognitionException.class )
    public void badLeadingDigit() throws Exception {
        recognize( prefix() + "A20034.3434E9" );
    }

    @Test( expected = RecognitionException.class )
    public void badDigitAfterDecimalPoint() throws Exception {
        recognize( prefix() + ".CAFE0009e-13" );
    }

    @Test( expected = RecognitionException.class )
    public void badDigitWithNoDecimalPoint() throws Exception {
        recognize( prefix() + "C129387e2" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mDOUBLE();
    }
    
    protected String prefix() {
        return "";
    }
}
