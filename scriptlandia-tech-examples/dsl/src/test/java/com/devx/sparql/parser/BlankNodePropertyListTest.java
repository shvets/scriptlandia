package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class BlankNodePropertyListTest extends ParserTest {
    @Test
    public void blankNodePropertyList() throws Exception {
        Tree tree = parseTreeFor( "[?aVerb ?anObject ]" );
        assertLeafNodes( tree, "[", "?aVerb", "?anObject", "]" );
    }

    @Test( expected = RecognitionException.class )
    public void notABlankNodePropertyList() throws Exception {
        parseTreeFor( "[]" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.blankNodePropertyList().getTree();
    }
}
