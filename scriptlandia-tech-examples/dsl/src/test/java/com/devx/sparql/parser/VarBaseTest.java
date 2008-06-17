/*
 * Copyright (c) 2007
 * Semantra, Inc. Addison, Texas 214.445.2900
 */
package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import com.devx.sparql.lexer.LexerTest;

public abstract class VarBaseTest extends LexerTest
{
    @Test
    public void question() throws Exception
    {
        recognize(prefix() + "varname");
    }

    @Test(expected = RecognitionException.class)
    public void noVarname() throws Exception
    {
        recognize(prefix());
    }

    @Test(expected = RecognitionException.class)
    public void nonQuestion() throws Exception
    {
        recognize("a");
    }

    public abstract String prefix();
}
