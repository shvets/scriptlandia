package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class VariableOrTermTest extends GraphTermTest {
    @Test
    public void variable() throws Exception {
        assertLeafNode( parseTreeFor( "?aVariable" ), "?aVariable" );
    }

    @Test( expected = RecognitionException.class )
    public void notAVariableNorAGraphTerm() throws Exception {
        parseTreeFor( "_" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.variableOrTerm().getTree();
    }
}
