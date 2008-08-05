package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class PrefixDeclTest extends ParserTest {
    @Test
    public void prefixDecl() throws Exception {
        Tree tree = parseTreeFor( "PREFIX abc: <http://foo.com>" );
        assertLeafNodes( tree, "PREFIX", "abc:", "<http://foo.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void notAPrefixDecl() throws Exception {
        parseTreeFor( "PREFIX abc: _" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.prefixDecl().getTree();
    }
}
