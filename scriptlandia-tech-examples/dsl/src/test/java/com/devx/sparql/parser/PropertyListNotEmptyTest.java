package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class PropertyListNotEmptyTest extends ParserTest {
    @Test
    public void verbObject() throws Exception {
        Tree tree = parseTreeFor( "?verb ?object" );
        assertLeafNodes( tree, "?verb", "?object" );
    }

    @Test
    public void verbObjectObject() throws Exception {
        Tree tree = parseTreeFor( "?verb ?object1, ?object2" );
        assertLeafNodes( tree, "?verb", "?object1", ",", "?object2" );
    }

    @Test
    public void verbObjectSemi() throws Exception {
        Tree tree = parseTreeFor( "?verb ?object;" );
        assertLeafNodes( tree, "?verb", "?object", ";" );
    }

    @Test
    public void verbObjectObjectSemi() throws Exception {
        Tree tree = parseTreeFor( "?verb ?object1, ?object2;" );
        assertLeafNodes( tree, "?verb", "?object1", ",", "?object2", ";" );
    }

    @Test
    public void verbObjectSemiVerbObject() throws Exception {
        Tree tree = parseTreeFor( "?verb1 ?object1; ?verb2 ?object2" );
        assertLeafNodes( tree, "?verb1", "?object1", ";", "?verb2", "?object2" );
    }

    @Test
    public void verbObjectObjectSemiVerbObject() throws Exception {
        Tree tree = parseTreeFor( "?verb1 ?object1, ?object2 ; ?verb2 ?object3" );
        assertLeafNodes( tree, "?verb1", "?object1", ",", "?object2", ";", "?verb2",
            "?object3" );
    }

    @Test
    public void verbObjectObjectSemiVerbObjectObject() throws Exception {
        Tree tree =
            parseTreeFor( "?verb1 ?object1, ?object2 ; ?verb2 ?object3, ?object4" );
        assertLeafNodes( tree, "?verb1", "?object1", ",", "?object2", ";", "?verb2",
            "?object3", ",", "?object4" );
    }

    @Test
    public void threeVerbObjectClauses() throws Exception {
        Tree tree =
            parseTreeFor( "?verb1 ?object1, ?object2 ; ?verb2 ?object3, ?object4; ?verb3 ?object5, ?object6;" );
        assertLeafNodes( tree, "?verb1", "?object1", ",", "?object2", ";", "?verb2",
            "?object3", ",", "?object4", ";", "?verb3", "?object5", ",", "?object6", ";" );
    }

    @Test( expected = RecognitionException.class )
    public void notAPropertyListNotEmpty() throws Exception {
        parseTreeFor( "?verb _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.propertyListNotEmpty().getTree();
    }
}
