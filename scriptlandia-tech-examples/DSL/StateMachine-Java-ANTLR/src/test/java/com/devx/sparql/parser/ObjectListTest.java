package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class ObjectListTest extends ObjectTest {
    @Test
    public void threeObjects() throws Exception {
        Tree tree = parseTreeFor( object() + ", " + object() + "   ," + object() );
        assertLeafNodes( tree, object(), ",", object(), ",", object() );
    }

    @Test( expected = RecognitionException.class )
    public void notAnObjectList() throws Exception {
        parseTreeFor( "_, _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.objectList().getTree();
    }
}
