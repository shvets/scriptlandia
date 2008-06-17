package com.devx.sparql.lexer;

import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public abstract class PnameCharactersPlusUnderscoreTest extends PnameCharactersBaseTest {
    @Test
    public void underscore() throws Exception {
        recognize( '_' );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPNAME_CHARS_PLUS_UNDERSCORE();
    }
}
