package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;
import com.devx.sparql.helper.SparqlAstBuilder;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WhereClauseTest extends ParserTest {
    @Test
    public void withWhere() throws Exception {
        Tree expected = SparqlAstBuilder.buildWhereClauseTree();
        Tree actual = parseTreeFor( "WHERE { ?s ?p ?o . } " );

        assertEquals( "Actual tree [" + actual.toStringTree() + "] did not match expected tree [" + expected.toStringTree() + "]", expected, actual );
    }

    @Test( expected = RecognitionException.class )
    public void notAWhereClause() throws Exception {
        parseTreeFor( "WHERE { _ ?p ?o }" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.whereClause().getTree();
    }
}
