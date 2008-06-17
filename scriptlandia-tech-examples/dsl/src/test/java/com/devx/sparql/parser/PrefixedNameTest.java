package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class PrefixedNameTest extends ParserTest {
    @Test
    public void localName() throws Exception {
        Tree tree = parseTreeFor( "   abc:askdjfhas  " );
        assertLeafNode( tree, "abc:askdjfhas" );
    }

    @Test
    public void namespace() throws Exception {
        Tree tree = parseTreeFor( "   a_b0.c111_dd.e_:  " );
        assertLeafNode( tree, "a_b0.c111_dd.e_:" );
    }

    @Test( expected = RecognitionException.class )
    public void notAPrefixedName() throws Exception {
        parseTreeFor( "     asdasd    " );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.prefixedName_return prefixedName = parser.prefixedName();
        return (Tree) prefixedName.getTree();
    }
}
