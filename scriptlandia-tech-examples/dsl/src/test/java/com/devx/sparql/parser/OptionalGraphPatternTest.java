package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class OptionalGraphPatternTest extends ParserTest {
    @Test
    public void optionalGraphPattern() throws Exception {
        Tree tree = parseTreeFor( "   OPTIONAL    { } " );
        assertNil( tree );
    }

    @Test( expected = RecognitionException.class )
    public void notAnOptionalGraphPattern() throws Exception {
        parseTreeFor( "OPTIONAL { _ } " );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.optionalGraphPattern().getTree();
    }
}
