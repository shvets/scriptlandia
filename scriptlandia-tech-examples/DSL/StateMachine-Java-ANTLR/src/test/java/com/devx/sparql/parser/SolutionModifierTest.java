package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class SolutionModifierTest extends LimitOffsetClausesTest {
    @Test
    public void empty() throws Exception {
        Tree tree = parseTreeFor( "" );
        assertLeafNodes( tree );
    }

    @Test
    public void orderClauseOnly() throws Exception {
        Tree tree = parseTreeFor( "ORDER BY ?aVar" );
        assertLeafNodes( tree, "ORDER", "BY", "?aVar" );
    }

    @Test
    public void orderClauseAndLimitOffsetClauses() throws Exception {
        Tree tree = parseTreeFor( "ORDER BY ?aVar LIMIT 3 OFFSET 4" );
        assertLeafNodes( tree, "ORDER", "BY", "?aVar", "LIMIT", "3", "OFFSET", "4" );
    }

    @Test( expected = RecognitionException.class )
    public void notASolutionModifier() throws Exception {
        parseTreeFor( "ORDER ?var BY" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.solutionModifier().getTree();
    }
}
