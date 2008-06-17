package com.devx.sparql.walker;

import static com.devx.sparql.helper.SparqlAstBuilder.buildSelectClauseTree;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.antlr.stringtemplate.StringTemplate;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SelectClauseTest extends WalkerTest {
    @Test
    public void shouldRecognizeSelectClauseAndPopulateTemplateAttributes() throws RecognitionException {
        Tree tree = buildSelectClauseTree();

        StringTemplate template = (StringTemplate) walkerFor( tree ).selectClause().getTemplate();

        assertEquals( "?s", template.getAttribute( "element" ).toString() );
    }
}
