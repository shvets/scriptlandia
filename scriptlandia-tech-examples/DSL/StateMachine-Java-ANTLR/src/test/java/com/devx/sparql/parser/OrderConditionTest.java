package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class OrderConditionTest extends ParserTest {
    @Test
    public void ascending() throws Exception {
        Tree tree = parseTreeFor( "ASC(2)" );
        assertLeafNodes( tree, "ASC", "(", "2", ")" );
    }

    @Test
    public void descending() throws Exception {
        Tree tree = parseTreeFor( "DESC    (6 )" );
        assertLeafNodes( tree, "DESC", "(", "6", ")" );
    }

    @Test
    public void constraint() throws Exception {
        Tree tree = parseTreeFor( "    (4  )" );
        assertLeafNodes( tree, "(", "4", ")" );
    }

    @Test
    public void variable() throws Exception {
        Tree tree = parseTreeFor( "    ?e   " );
        assertLeafNode( tree, "?e" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnOrderCondition() throws Exception {
        parseTreeFor( "ASC(_)" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.orderCondition().getTree();
    }
}
