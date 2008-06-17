package com.devx.sparql.walker;

import com.devx.sparql.SparqlWalker;
import static com.devx.sparql.helper.SparqlAstBuilder.buildTree;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.antlr.stringtemplate.StringTemplate;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ElementTest extends WalkerTest {
    @Test
    public void shouldRecognizeElementAndPopulateTemplateAttribute() throws RecognitionException {
        Tree tree = buildTree( SparqlWalker.QUESTION_VARNAME, "?s" );

        SparqlWalker walker = walkerFor( tree );

        StringTemplate template = (StringTemplate) walker.element().getTemplate();
        assertEquals( "?s", template.getAttribute( "name" ) );
        assertEquals( "?s", template.toString() );
    }
}
