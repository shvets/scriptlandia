package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class StringTest extends ParserTest {
    @Test
    public void singleQuoted() throws Exception {
        Tree tree = parseTreeFor( "'i am \\'single-quoted\\'.'" );
        assertLeafNode( tree, "'i am \\'single-quoted\\'.'" );
    }

    @Test
    public void doubleQuoted() throws Exception {
        Tree tree = parseTreeFor( "\"i am \\\"double-quoted\\\".\"" );
        assertLeafNode( tree, "\"i am \\\"double-quoted\\\".\"" );
    }

    @Test
    public void longSingleQuoted() throws Exception {
        Tree tree = parseTreeFor( "'''i also am a\n\n  'single-quoted'\t\t\nstring.'''" );
        assertLeafNode( tree, "'''i also am a\n\n  'single-quoted'\t\t\nstring.'''" );
    }

    @Test
    public void longDoubleQuoted() throws Exception {
        String quoted = "\"\"\"i also am a\n\n  \"double-quoted\"\t\t\nstring.\"\"\"";
        Tree tree = parseTreeFor( quoted );
        assertLeafNode( tree, quoted );
    }

    @Test( expected = RecognitionException.class )
    public void notAString() throws Exception {
        parseTreeFor( "not a string" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.string_return string = parser.string();
        return (Tree) string.getTree();
    }
}
