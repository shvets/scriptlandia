package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class PrologueTest extends ParserTest {
    @Test
    public void empty() throws Exception {
        Tree tree = parseTreeFor( "" );
        assertLeafNodes( tree );
    }

    @Test
    public void baseOnly() throws Exception {
        Tree tree = parseTreeFor( "BASE <http://x.com>" );
        assertLeafNodes( tree, "BASE", "<http://x.com>" );
    }

    @Test
    public void singlePrefixOnly() throws Exception {
        Tree tree = parseTreeFor( "PREFIX a: <http://x.com>" );
        assertLeafNodes( tree, "PREFIX", "a:", "<http://x.com>" );
    }

    @Test
    public void manyPrefixesOnly() throws Exception {
        Tree tree = parseTreeFor( "PREFIX a: <http://x.com> PREFIX b: <http://y.com>" );
        assertLeafNodes( tree, "PREFIX", "a:", "<http://x.com>", "PREFIX", "b:",
            "<http://y.com>" );
    }

    @Test
    public void baseAndManyPrefixes() throws Exception {
        Tree tree = parseTreeFor( "BASE <http://w.com> PREFIX a: <http://x.com> PREFIX b: <http://y.com>" );
        assertLeafNodes( tree, "BASE", "<http://w.com>", "PREFIX", "a:",
            "<http://x.com>", "PREFIX", "b:", "<http://y.com>" );
    }

    @Test( expected = RecognitionException.class )
    public void notAPrologue() throws Exception {
        parseTreeFor( "BASE _ PREFIX a: <http://w.com>" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        return (Tree) parser.prologue().getTree();
    }
}
