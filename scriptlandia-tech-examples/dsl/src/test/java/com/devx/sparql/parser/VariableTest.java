package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class VariableTest extends ParserTest {
    @Test
    public void questionVariable() throws Exception {
        assertLeafNode( parseTreeFor( "    ?subject   " ), "?subject" );
    }

    @Test
    public void dollarVariable() throws Exception {
        assertLeafNode( parseTreeFor( "    $someVar   " ), "$someVar" );
    }

    @Test( expected = RecognitionException.class )
    public void notAVariable() throws Exception {
        parseTreeFor( "_ASD_" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.variable_return variable = parser.variable();
        return (Tree) variable.getTree();
    }
}
