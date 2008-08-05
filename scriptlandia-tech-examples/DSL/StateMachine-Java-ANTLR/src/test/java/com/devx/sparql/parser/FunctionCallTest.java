package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class FunctionCallTest extends ParserTest {
    @Test
    public void noArgs() throws Exception {
        Tree tree = parseTreeFor( "devx:superFunction()" );
        assertLeafNodes( tree, "devx:superFunction", "()" );
    }

    @Test
    public void oneArg() throws Exception {
        Tree tree = parseTreeFor( "devx:superFunction(1)" );
        assertLeafNodes( tree, "devx:superFunction", "(", "1", ")" );
    }

    @Test
    public void threeArgs() throws Exception {
        Tree tree = parseTreeFor( "devx:superFunction(1, 2, 3 )" );
        assertLeafNodes( tree, "devx:superFunction", "(", "1", ",", "2", ",", "3", ")" );
    }

    @Test( expected = RecognitionException.class )
    public void notAFunctionCall() throws Exception {
        parseTreeFor( "_" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.functionCall().getTree();
    }
}
