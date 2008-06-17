package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class IriReferenceLexerTest extends LexerTest {
    @Test
    public void empty() throws Exception {
        assertRecognized( "<>" );
    }

    @Test
    public void normal() throws Exception {
        assertRecognized( "<http://www.devx.com/ontologies/>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningLeftAngleBracket() throws Exception {
        recognize( "<http<www.devx.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningRightAngleBracket() throws Exception {
        recognize( ">http://www.devx.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningDoubleQuote() throws Exception {
        recognize( "<http://www\"devx.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningLeftCurly() throws Exception {
        recognize( "<http{<www.devx.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningRightCurly() throws Exception {
        recognize( "<http://www}devx.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningPipe() throws Exception {
        recognize( "<http://www.devx|com>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningBacktick() throws Exception {
        recognize( "<http://www.dev`x.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningBackslash() throws Exception {
        recognize( "<http://www.devx.com\\>" );
    }

    @Test( expected = RecognitionException.class )
    public void withInterveningSpace() throws Exception {
        recognize( "<http://www.devx   .com>" );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mIRI_REFERENCE();
    }

    @Override
    protected int expectedTokenType() {
        return SparqlLexer.IRI_REFERENCE;
    }
}
