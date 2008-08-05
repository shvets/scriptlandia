package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class DatasetClauseTest extends ParserTest {
    @Test
    public void withDefaultGraphClause() throws Exception {
        Tree tree = parseTreeFor( "FROM <http://www.devx.com/ontologies/a-named-graph>" );
        assertLeafNodes( tree, "FROM", "<http://www.devx.com/ontologies/a-named-graph>" );
    }

    @Test
    public void withNamedGraphClause() throws Exception {
        Tree tree =
            parseTreeFor( "FROM NAMED <http://www.devx.com/ontologies/a-named-graph>" );
        assertLeafNodes( tree, "FROM", "NAMED",
            "<http://www.devx.com/ontologies/a-named-graph>" );
    }

    @Test( expected = RecognitionException.class )
    public void notADatasetClauseTest() throws Exception {
        parseTreeFor( "FROM _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.datasetClause().getTree();
    }
}
