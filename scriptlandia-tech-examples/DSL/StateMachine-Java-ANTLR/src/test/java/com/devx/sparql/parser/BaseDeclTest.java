package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class BaseDeclTest extends ParserTest {
    @Test
    public void baseDecl() throws Exception {
        Tree tree = parseTreeFor( "BASE <http://bar.com>" );
        assertLeafNodes( tree, "BASE", "<http://bar.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void notAPrefixDecl() throws Exception {
        parseTreeFor( "BASE _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.baseDecl().getTree();
    }
}
