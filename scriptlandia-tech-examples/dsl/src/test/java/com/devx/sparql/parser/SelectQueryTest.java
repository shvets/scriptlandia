package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;
import com.devx.sparql.helper.SparqlAstBuilder;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SelectQueryTest extends ParserTest {
    @Test
    public void shouldRecognizeSelectQueryAndCreateTree() throws RecognitionException {
        Tree expectedSelect = SparqlAstBuilder.buildSelectClauseTree();
        Tree expectedWhere = SparqlAstBuilder.buildWhereClauseTree();

        Tree tree = parseTreeFor( "SELECT ?s WHERE { ?s ?p ?o .}" );

        assertEquals( expectedSelect, tree.getChild( 0 ) );
        assertEquals( expectedWhere, tree.getChild( 1 ) );
    }

    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.selectQuery().getTree();
    }
}
