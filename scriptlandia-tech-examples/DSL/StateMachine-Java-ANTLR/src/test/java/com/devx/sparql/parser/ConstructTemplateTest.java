package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class ConstructTemplateTest extends ParserTest {
    @Test
    public void empty() throws Exception {
        Tree tree = parseTreeFor( " {   }   " );
        assertLeafNodes( tree, "{", "}" );
    }

    @Test
    public void oneTripleNoPeriod() throws Exception {
        Tree tree = parseTreeFor( " { ?s ?p ?o  }   " );
        assertLeafNodes( tree, "{", "TRIPLE", "}" );
    }

    @Test
    public void oneTripleWithPeriod() throws Exception {
        Tree tree = parseTreeFor( " { ?s ?p ?o.  }   " );
        assertLeafNodes( tree, "{", "TRIPLE", ".", "}" );
    }

    @Test
    public void twoTriplesTwoPeriods() throws Exception {
        Tree tree = parseTreeFor( " { ?s ?p ?o. ?subject ?verb ?object . }" );
        assertLeafNodes( tree, "{", "TRIPLE", ".", "TRIPLE", ".", "}" );
    }

    @Test( expected = RecognitionException.class )
    public void notAConstructTemplate() throws Exception {
        parseTreeFor( "{ ?s ?p _ . }" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.constructTemplate().getTree();
    }
}
