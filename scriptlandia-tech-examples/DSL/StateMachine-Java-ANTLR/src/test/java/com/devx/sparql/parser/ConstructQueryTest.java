package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

public class ConstructQueryTest extends ParserTest {
    @Test
    public void constructTemplateAndWhereClauseOnly() throws Exception {
        Tree tree = parseTreeFor( "CONSTRUCT { ?s ?p ?o } WHERE { ?s ?p ?o . } " );
        // TODO: Handle construct block
        assertNil( tree );
    }

    @Test
    public void withSolutionModifier() throws Exception {
        Tree tree = parseTreeFor( "CONSTRUCT { ?s ?p ?o } WHERE { ?s ?p ?o . } LIMIT 2" );
        // TODO: Handle construct block
        assertNil( tree );
    }

    @Test
    public void withDatasetClauses() throws Exception {
        Tree tree = parseTreeFor( "CONSTRUCT { ?s ?p ?o } FROM <http://x.com>"
            + " FROM <http://y.com> WHERE { ?s ?p ?o . }" );
        // TODO: Handle construct block
        assertNil( tree );
    }

    @Test
    public void withDatasetClausesAndSolutionModifier() throws Exception {
        Tree tree = parseTreeFor( "CONSTRUCT { ?s ?p ?o } FROM <http://x.com>"
            + " FROM <http://y.com> WHERE { ?s ?p ?o . } OFFSET 3" );
        // TODO: Handle construct block
        assertNil( tree );
    }

    @Test( expected = RecognitionException.class )
    public void notAConstructQuery() throws Exception {
        parseTreeFor( "CONSTRUCT { ?s ?p ?o } WHERE { _ _ _ }" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.constructQuery().getTree();
    }
}
