package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class PropertyListTest extends PropertyListNotEmptyTest {
    @Test
    public void empty() throws Exception {
        Tree tree = parseTreeFor( "" );
        assertLeafNodes( tree );
    }

    @Test( expected = RecognitionException.class )
    public void notAPropertyList() throws Exception {
        parseTreeFor( "?aVerb" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.propertyList().getTree();
    }
}
