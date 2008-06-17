package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class GraphTermTest extends ParserTest {
    @Test
    public void iriReference() throws Exception {
        assertLeafNode( parseTreeFor( "<http://www.devx.com/ontologies/>" ),
            "<http://www.devx.com/ontologies/>" );
    }

    @Test
    public void rdfLiteral() throws Exception {
        assertLeafNode( parseTreeFor( "'asdfasdf'" ), "'asdfasdf'" );
    }

    @Test
    public void numericLiteral() throws Exception {
        assertLeafNode( parseTreeFor( "123123" ), "123123" );
    }

    @Test
    public void booleanLiteral() throws Exception {
        assertLeafNode( parseTreeFor( "true" ), "true" );
    }

    @Test
    public void blankNode() throws Exception {
        assertLeafNode( parseTreeFor( "[]" ), "[]" );
    }

    @Test
    public void nil() throws Exception {
        assertLeafNode( parseTreeFor( "()" ), "()" );
    }

    @Test( expected = RecognitionException.class )
    public void notAGraphTerm() throws Exception {
        parseTreeFor( " +  " );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.graphTerm().getTree();
    }
}
