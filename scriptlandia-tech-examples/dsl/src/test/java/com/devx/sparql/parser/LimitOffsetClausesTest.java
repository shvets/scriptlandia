package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class LimitOffsetClausesTest extends LimitClauseTest {
    @Test
    public void offsetClause() throws Exception {
        Tree tree = parseTreeFor( "OFFSET 1" );
        assertLeafNodes( tree, "OFFSET", "1" );
    }

    @Test
    public void limitAndOffset() throws Exception {
        Tree tree = parseTreeFor( "LIMIT 1 OFFSET 2" );
        assertLeafNodes( tree, "LIMIT", "1", "OFFSET", "2" );
    }

    @Test
    public void offsetAndLimit() throws Exception {
        Tree tree = parseTreeFor( "OFFSET 3 LIMIT 4" );
        assertLeafNodes( tree, "OFFSET", "3", "LIMIT", "4" );
    }

    @Test( expected = RecognitionException.class )
    public void notALimitOffsetClauses() throws Exception {
        parseTreeFor( "LIMIT _ LIMIT 2" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.limitOffsetClauses().getTree();
    }
}
