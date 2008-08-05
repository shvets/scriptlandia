package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class VariableOrIriReferenceTest extends VariableTest {
    @Test
    public void iriReference() throws Exception {
        assertLeafNode( parseTreeFor( "<http://www.devx.com/ontologies/>" ),
            "<http://www.devx.com/ontologies/>" );
    }

    @Test( expected = RecognitionException.class )
    public void notAVariableNorAnIriReference() throws Exception {
        parseTreeFor( "_" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.variableOrIriReference().getTree();
    }
}
