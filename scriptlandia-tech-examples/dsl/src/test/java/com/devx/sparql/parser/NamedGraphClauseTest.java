package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class NamedGraphClauseTest extends ParserTest {
    @Test
    public void namedGraphClause() throws Exception {
        Tree tree = parseTreeFor( "NAMED <http://www.devx.com/ontologies/a-named-graph>" );
        assertLeafNodes( tree, "NAMED", "<http://www.devx.com/ontologies/a-named-graph>" );
    }

    @Test( expected = RecognitionException.class )
    public void notANamedGraphClause() throws Exception {
        parseTreeFor( "NAMED _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.namedGraphClause().getTree();
    }
}
