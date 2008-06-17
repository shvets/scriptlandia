package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class VerbTest extends VariableOrIriReferenceTest {
    @Test
    public void a() throws Exception {
        assertLeafNode( parseTreeFor( " a " ), "a" );
    }

    @Test( expected = RecognitionException.class )
    public void notAVerb() throws Exception {
        parseTreeFor( "_" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.verb().getTree();
    }
}
