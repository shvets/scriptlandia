package com.devx.sparql.walker;

import static com.devx.sparql.helper.SparqlAstBuilder.buildQueryTree;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.antlr.stringtemplate.StringTemplate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class QueryTest extends WalkerTest {
    @Test
    public void shouldRecognizeQueryAndPopulateTemplateAttributes() throws RecognitionException {
        Tree tree = buildQueryTree();

        StringTemplate template = (StringTemplate) walkerFor( tree ).query().getTemplate();

        assertEquals( 2, template.getAttributes().size() );
        assertTrue( template.getAttribute( "selectClause" ) instanceof StringTemplate );
        assertTrue( template.getAttribute( "whereClause" ) instanceof StringTemplate );
    }
}
