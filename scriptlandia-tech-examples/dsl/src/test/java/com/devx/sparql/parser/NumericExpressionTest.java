package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import com.devx.sparql.SparqlParser;

public class NumericExpressionTest extends AdditiveExpressionTest {
    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.numericExpression().getTree();
    }

    protected String numeric() {
        return additive();
    }
}
