package com.devx.sparql.walker;

import com.devx.sparql.SparqlWalker;
import static com.devx.sparql.helper.SparqlAstBuilder.buildWhereClauseTree;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.antlr.stringtemplate.StringTemplate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.List;

public class WhereClauseTest extends WalkerTest {
    @Test
    public void shouldRecognizeWhereClauseAndPopulateTemplateAttributes() throws RecognitionException {
        Tree whereClauseTree = buildWhereClauseTree();

        SparqlWalker walker = walkerFor( whereClauseTree );

        StringTemplate whereTemplate = (StringTemplate) walker.whereClause().getTemplate();
        
        assertEquals( 1, whereTemplate.getAttributes().size() );
        List tripleTemplates = (List) whereTemplate.getAttribute( "triples" );
        assertEquals( 1, tripleTemplates.size() );
        assertTrue( tripleTemplates.get( 0 ) instanceof StringTemplate );
    }
}
