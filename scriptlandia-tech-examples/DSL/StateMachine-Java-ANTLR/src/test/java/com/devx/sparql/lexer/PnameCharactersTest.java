package com.devx.sparql.lexer;

import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class PnameCharactersTest extends PnameCharactersPlusUnderscoreTest {
    @Test
    public void hyphen() throws Exception {
        recognize( '-' );
    }

    @Test
    public void digits() throws Exception {
        recognizeRange( '0', '9' );
    }

    @Test
    public void unicodeMiddleDot() throws Exception {
        recognize( '\u00b7' );
    }

    @Test
    public void betweenBlocks3And4() throws Exception {
        recognizeRange( '\u0300', '\u036f' );
    }

    @Test
    public void undertieAndCharacterTie() throws Exception {
        recognizeRange( '\u203f', '\u2040' );
    }

    @Override
    protected void fireLexerRule( SparqlLexer lexer ) throws Exception {
        lexer.mPNAME_CHARS();
    }
}
