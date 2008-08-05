package com.devx.sparql.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import com.devx.sparql.SparqlParser;

public class RdfLiteralTest extends StringTest {
    @Test
    public void singleQuotedStringPlusLanguageTag() throws Exception {
        Tree tree = parseTreeFor( "    'asdfasdf'@en-US   " );
        assertLeafNodes( tree, "'asdfasdf'", "@en-US" );
    }

    @Test
    public void doubleQuotedStringPlusLanguageTag() throws Exception {
        Tree tree = parseTreeFor( "    \"chat\"@fr    " );
        assertLeafNodes( tree, "\"chat\"", "@fr" );
    }

    @Test
    public void longSingleQuotedStringPlusLanguageTag() throws Exception {
        Tree tree = parseTreeFor( "    '''Hey man,\n\twhat's up'''@en-GB   " );
        assertLeafNodes( tree, "'''Hey man,\n\twhat's up'''", "@en-GB" );
    }

    @Test
    public void longDoubleQuotedStringPlusLanguageTag() throws Exception {
        Tree tree = parseTreeFor( "\"\"\"Maybe I'm not\n\t\"the norm\".\"\"\"@de" );
        assertLeafNodes( tree, "\"\"\"Maybe I'm not\n\t\"the norm\".\"\"\"", "@de" );
    }

    @Test
    public void singleQuotedStringPlusIriReference() throws Exception {
        Tree tree = parseTreeFor( "    'asdfasdf'   ^^ <http://whatsup.org>   " );
        assertLeafNodes( tree, "'asdfasdf'", "^^", "<http://whatsup.org>" );
    }

    @Test
    public void doubleQuotedStringPlusLocalName() throws Exception {
        Tree tree = parseTreeFor( "    \"chat\" ^^ xmlns:scoobydoo    " );
        assertLeafNodes( tree, "\"chat\"", "^^", "xmlns:scoobydoo" );
    }

    @Test
    public void longSingleQuotedStringPlusNamespace() throws Exception {
        Tree tree = parseTreeFor( "    '''Hey man,\n\twhat's up'''^^name.space:   " );
        assertLeafNodes( tree, "'''Hey man,\n\twhat's up'''", "^^", "name.space:" );
    }

    @Test
    public void longDoubleQuotedStringPlusIriReference() throws Exception {
        Tree tree = parseTreeFor( "\"\"\"Howdy doo\"\"\" ^^  <http://www.w3.org> " );
        assertLeafNodes( tree, "\"\"\"Howdy doo\"\"\"", "^^", "<http://www.w3.org>" );
    }

    @Test( expected = RecognitionException.class )
    public void notAnRdfLiteral() throws Exception {
        parseTreeFor( " 'asdasd'^^11" );
    }

    @Override
    protected Tree fireParserRule( SparqlParser parser ) throws RecognitionException {
        SparqlParser.rdfLiteral_return rdfLiteral = parser.rdfLiteral();
        return (Tree) rdfLiteral.getTree();
    }
}
