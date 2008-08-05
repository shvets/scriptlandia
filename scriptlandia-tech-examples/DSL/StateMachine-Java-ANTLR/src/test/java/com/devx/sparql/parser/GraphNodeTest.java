package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class GraphNodeTest extends VariableOrTermTest {
    @Test
    public void triplesNode() throws Exception {
        Tree tree = parseTreeFor( "[?aVerb ?anObject ]" );
        assertLeafNodes( tree, "[", "?aVerb", "?anObject", "]" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.graphNode().getTree();
    }
}
