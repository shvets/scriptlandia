package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;
import com.devx.sparql.lexer.LexerTest;

public class LongDoubleQuotedStringTest extends LexerTest {
    @Test
    public void empty() throws Exception {
        assertRecognized( "\"\"\"\"\"\"" );
    }

    @Test
    public void multiLine() throws Exception {
        assertRecognized( "\"\"\"Double-quoted\n\"strings\" don't give me a\t\tlick of trouble.\"\"\"" );
    }

    @Test( expected = RecognitionException.class )
    public void mismatchedQuotes() throws Exception {
        recognize( "\"\"\"My leading and trailing\n\nquotes are \"mismatched\".\"\"" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mLONG_DOUBLE_QUOTED_STRING();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.LONG_DOUBLE_QUOTED_STRING;
    }
}
