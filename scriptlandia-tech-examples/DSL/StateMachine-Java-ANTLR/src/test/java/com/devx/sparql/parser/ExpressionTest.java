package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import com.devx.sparql.SparqlParser;

public class ExpressionTest extends ConditionalOrExpressionTest {
    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.expression().getTree();
    }
}
