package com.devx.sparql.parser;

import static com.devx.sparql.parser.Expressions.*;
import com.devx.sparql.SparqlParser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class IriReferenceOrFunctionTest extends IriReferenceParserTest {
    @Test
    public void function() throws Exception {
        Tree tree = parseTreeFor( "x:fooBar(" + SIMPLE_EXPRESSION + ")" );
        assertLeafNodes( tree, "x:fooBar", "(", SIMPLE_EXPRESSION, ")" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.iriReferenceOrFunction_return iriReferenceOrFunction =
            parser.iriReferenceOrFunction();
        return (Tree) iriReferenceOrFunction.getTree();
    }
}
