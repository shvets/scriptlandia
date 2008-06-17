package com.devx.sparql.walker;

import com.devx.sparql.SparqlWalker;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import java.io.Reader;
import java.io.InputStreamReader;

public abstract class WalkerTest {
    protected SparqlWalker walkerFor( Tree tree ) {
        Reader templatesIn = new InputStreamReader( getClass().getResourceAsStream( "/templates/sparql.stg" ) );
        StringTemplateGroup templates = new StringTemplateGroup( templatesIn, DefaultTemplateLexer.class );

        SparqlWalker walker = new SparqlWalker( new CommonTreeNodeStream( tree ) );
        walker.setTemplateLib( templates );

        return walker;
    }
}
