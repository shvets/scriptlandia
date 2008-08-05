package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class LimitClauseTest extends ParserTest {
    @Test
    public void limitClause() throws Exception {
        Tree tree = parseTreeFor( "LIMIT 4" );
        assertLeafNodes( tree, "LIMIT", "4" );
    }

    @Test( expected = RecognitionException.class )
    public void notALimitClause() throws Exception {
        parseTreeFor( "LIMIT ?" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.limitClause().getTree();
    }
}
