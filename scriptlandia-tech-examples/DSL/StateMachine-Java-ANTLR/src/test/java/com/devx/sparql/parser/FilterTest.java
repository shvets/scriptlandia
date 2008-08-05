package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class FilterTest extends ParserTest {
    @Test
    public void filter() throws Exception {
        Tree tree = parseTreeFor( "  FILTER   (2)" );
        assertLeafNodes( tree, "FILTER", "(", "2", ")" );
    }

    @Test( expected = RecognitionException.class )
    public void notAFilter() throws Exception {
        parseTreeFor( "FILTER _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.filter().getTree();
    }
}
