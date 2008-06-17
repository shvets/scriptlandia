package com.devx.sparql.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.Tree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static java.util.Arrays.asList;
import java.util.List;

import com.devx.sparql.SparqlParser;
import com.devx.sparql.SparqlLexer;

public abstract class ParserTest {
    protected void assertNil( Tree tree ) {
        assertTrue( tree.isNil() );
    }

    protected void assertTreeAndChildren( Tree tree, int type, String... texts ) {
        assertEquals( type, tree.getType() );
        assertLeafNodes( tree, asList( texts ) );
    }

    protected void assertLeafNodes( Tree tree, String... texts ) {
        assertLeafNodes( tree, asList( texts ) );
    }

    protected void assertLeafNodes( Tree tree, List<String> texts ) {
        assertEquals( texts.size(), tree.getChildCount() );

        for (int i = 0; i < texts.size(); ++i)
            assertLeafNode( tree.getChild( i ), texts.get( i ) );
    }

    protected void assertLeafNode( Tree tree, String text ) {
        assertEquals( text, tree.getText() );
    }

    protected SparqlParser parserOver( String text ) {
        SparqlLexer lexer = new SparqlLexer( new ANTLRStringStream( text ) );
        TokenStream tokenStream = new CommonTokenStream( lexer );
        return new SparqlParser( tokenStream );
    }

    protected Tree parseTreeFor( String text ) throws RecognitionException {
        SparqlParser parser = parserOver( text );
        return fireParserRule( parser );
    }

    protected abstract Tree fireParserRule( SparqlParser parser )
        throws RecognitionException;
}