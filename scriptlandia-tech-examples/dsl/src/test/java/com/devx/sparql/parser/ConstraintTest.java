package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class ConstraintTest extends BuiltInCallTest {
    @Test
    public void brackettedExpression() throws Exception {
        Tree tree = parseTreeFor( " (" + Expressions.SIMPLE_EXPRESSION + "   ) " );
        assertLeafNodes( tree, "(", Expressions.SIMPLE_EXPRESSION, ")" );
    }

    @Test
    public void functionCall() throws Exception {
        Tree tree = parseTreeFor( "devx:superFunction(1, 2, 3 )" );
        assertLeafNodes( tree, "devx:superFunction", "(", "1", ",", "2", ",", "3", ")" );
    }

    @Test( expected = RecognitionException.class )
    public void notAConstraint() throws Exception {
        parseTreeFor( "()" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.constraint().getTree();
    }
}
