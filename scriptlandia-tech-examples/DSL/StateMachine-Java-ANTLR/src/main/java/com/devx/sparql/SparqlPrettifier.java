package com.devx.sparql;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class SparqlPrettifier {
    public String prettify( String sparql ) throws Exception {
        ByteArrayInputStream sparqlStream = new ByteArrayInputStream( sparql.getBytes() );

        ANTLRInputStream source = new ANTLRInputStream( sparqlStream );
        SparqlLexer lexer = new SparqlLexer( source );
        CommonTokenStream tokens = new CommonTokenStream( lexer );

        SparqlParser parser = new SparqlParser( tokens );
        Tree ast = (Tree) parser.query().getTree();
        CommonTreeNodeStream nodes = new CommonTreeNodeStream( ast );

        Reader templatesIn = new InputStreamReader( getClass().getResourceAsStream( "/templates/sparql.stg" ) );
        StringTemplateGroup templates = new StringTemplateGroup( templatesIn, DefaultTemplateLexer.class );

        SparqlWalker walker = new SparqlWalker( nodes );
        walker.setTemplateLib( templates );
        
        return walker.query().toString();
    }
}
