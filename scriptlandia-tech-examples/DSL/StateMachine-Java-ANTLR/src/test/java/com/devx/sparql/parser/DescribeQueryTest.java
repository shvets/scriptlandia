package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class DescribeQueryTest extends ParserTest {
    @Test
    public void asteriskOnly() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE *" );
        assertLeafNodes( tree, "DESCRIBE", "*" );
    }

    @Test
    public void asteriskAndSolutionModifier() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE * LIMIT 2" );
        assertLeafNodes( tree, "DESCRIBE", "*", "LIMIT", "2" );
    }

    @Test
    public void variableOnly() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE ?v" );
        assertLeafNodes( tree, "DESCRIBE", "?v" );
    }

    @Test
    public void variableAndSolutionModifier() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE ?s OFFSET 3" );
        assertLeafNodes( tree, "DESCRIBE", "?s", "OFFSET", "3" );
    }

    @Test
    public void iriReferenceOnly() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE <http://foo.com>" );
        assertLeafNodes( tree, "DESCRIBE", "<http://foo.com>" );
    }

    @Test
    public void iriReferenceAndSolutionModifier() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE <http://bar.com> LIMIT 5" );
        assertLeafNodes( tree, "DESCRIBE", "<http://bar.com>", "LIMIT", "5" );
    }

    @Test
    public void multipleVariablesOnly() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE ?s ?p ?o" );
        assertLeafNodes( tree, "DESCRIBE", "?s", "?p", "?o" );
    }

    @Test
    public void multipleVariablesAndSolutionModifier() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE ?s ?p ?o OFFSET 4" );
        assertLeafNodes( tree, "DESCRIBE", "?s", "?p", "?o", "OFFSET", "4" );
    }

    @Test
    public void multipleIriReferencesOnly() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE <http://foo.com> <http://baz.com>" );
        assertLeafNodes( tree, "DESCRIBE", "<http://foo.com>", "<http://baz.com>" );
    }

    @Test
    public void multipleIriReferencesAndSolutionModifier() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE <http://bar.com> <http://biz.com> LIMIT 1" );
        assertLeafNodes( tree, "DESCRIBE", "<http://bar.com>", "<http://biz.com>",
            "LIMIT", "1" );
    }

    @Test
    public void multipleVariablesWithWhereClause() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE ?s ?p ?o WHERE { ?s ?p ?o . }" );
        assertLeafNodes( tree, "DESCRIBE", "?s", "?p", "?o", "WHERE" );
    }

    @Test
    public void multipleVariablesWithDatasetClausesWhereClause() throws Exception {
        Tree tree = parseTreeFor( "DESCRIBE ?s ?p ?o FROM NAMED <http://foo.com>"
            + " FROM <http://bar.com> WHERE { ?s ?p ?o . }" );
        assertLeafNodes( tree, "DESCRIBE", "?s", "?p", "?o", "FROM", "NAMED",
            "<http://foo.com>", "FROM", "<http://bar.com>", "WHERE" );
    }

    @Test( expected = RecognitionException.class )
    public void notADescribeQuery() throws Exception {
        parseTreeFor( "DESCRIBE ?s WHERE { _ _ _ }" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.describeQuery().getTree();
    }
}
