package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class OffsetClauseTest extends ParserTest {
    @Test
    public void offsetClause() throws Exception {
        Tree tree = parseTreeFor( "OFFSET 23" );
        assertLeafNodes( tree, "OFFSET", "23" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnOffsetClause() throws Exception {
        parseTreeFor( "OFFSET _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.offsetClause().getTree();
    }
}
