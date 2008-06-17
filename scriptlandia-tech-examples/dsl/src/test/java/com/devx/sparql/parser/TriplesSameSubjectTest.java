package com.devx.sparql.parser;

import com.devx.sparql.SparqlParser;
import static com.devx.sparql.SparqlParser.TRIPLE;
import static com.devx.sparql.helper.SparqlAstBuilder.buildTripleTree;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TriplesSameSubjectTest extends ParserTest {
    @Test
    public void variableOrTermAndPropertyListNotEmpty() throws Exception {
        Tree expected = buildTripleTree();
        Tree actual = parseTreeFor( "?s ?p ?o" );

        assertEquals( expected, actual );
    }

    @Test
    public void triplesNodeAndPropertyList() throws Exception {
        Tree tree = parseTreeFor( "(?var1 ?var2) ?verb ?object" );
        assertTreeAndChildren( tree, TRIPLE, "(", "?var1", "?var2", ")", "?verb", "?object" );
    }

    @Test( expected = RecognitionException.class )
    public void notATriplesSameSubject() throws Exception {
        parseTreeFor( "_ ?verb ?object" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.triplesSameSubject().getTree();
    }
}
