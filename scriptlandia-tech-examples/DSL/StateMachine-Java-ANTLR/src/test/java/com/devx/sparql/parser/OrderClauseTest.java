package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class OrderClauseTest extends ParserTest {
    @Test
    public void oneCondition() throws Exception {
        Tree tree = parseTreeFor( "  ORDER    BY ASC(1)    " );
        assertLeafNodes( tree, "ORDER", "BY", "ASC", "(", "1", ")" );
    }

    @Test
    public void twoConditions() throws Exception {
        Tree tree = parseTreeFor( "  ORDER    BY ASC(1)    DESC(4)" );
        assertLeafNodes( tree, "ORDER", "BY", "ASC", "(", "1", ")", "DESC", "(", "4", ")" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnOrderClause() throws Exception {
        parseTreeFor( "ORDER BY _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.orderClause().getTree();
    }
}
