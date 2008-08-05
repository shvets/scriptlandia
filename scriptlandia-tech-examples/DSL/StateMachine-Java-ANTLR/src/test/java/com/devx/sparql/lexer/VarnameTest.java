package com.devx.sparql.lexer;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.SparqlLexer;

public class VarnameTest extends PnameCharactersPlusUnderscoreTest
{
    @Test
    public void digits() throws Exception
    {
        for (char ch = '0'; ch <= '9'; ++ch)
            assertRecognized(prefix() + ch);
    }

    @Test
    public void varName() throws Exception
    {
        assertRecognized(prefix() + "abc0349\u00b7\u0300\u036f___\u203f\u2040");
    }

    @Test(expected = RecognitionException.class)
    public void nonVarName() throws Exception
    {
        assertRecognized(prefix() + ".abcde");
    }

    @Override
    protected void fireLexerRule(SparqlLexer lexer) throws Exception
    {
        lexer.mVARNAME();
    }

    protected String prefix()
    {
        return "";
    }
}
