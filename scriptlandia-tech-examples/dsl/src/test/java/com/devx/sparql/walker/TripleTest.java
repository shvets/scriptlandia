package com.devx.sparql.walker;

import com.devx.sparql.SparqlWalker;
import static com.devx.sparql.helper.SparqlAstBuilder.buildTripleTree;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.antlr.stringtemplate.StringTemplate;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TripleTest extends WalkerTest {
    @Test
    public void shouldRecognizeTripleAndPopulateTemplateAttributes() throws RecognitionException {
        Tree tree = buildTripleTree();

        SparqlWalker walker = walkerFor( tree );

        StringTemplate template = (StringTemplate) walker.triple().getTemplate();
        assertEquals( "?s", template.getAttribute( "subject" ).toString() );
        assertEquals( "?p", template.getAttribute( "predicate" ).toString() );
        assertEquals( "?o", template.getAttribute( "object" ).toString() );
    }
}
