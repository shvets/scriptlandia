package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class IriReferenceParserTest extends PrefixedNameTest {
    @Test
    public void iriReference() throws Exception {
        assertLeafNode( parseTreeFor( "<http://www.devx.com/ontologies/>" ),
            "<http://www.devx.com/ontologies/>" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnIriReference() throws Exception {
        parseTreeFor( "!@#!@#" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.iriReference_return iriReference = parser.iriReference();
        return (Tree) iriReference.getTree();
    }
}
