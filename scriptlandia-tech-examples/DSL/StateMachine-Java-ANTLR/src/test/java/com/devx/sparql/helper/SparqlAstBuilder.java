package com.devx.sparql.helper;

import com.devx.sparql.SparqlParser;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.tree.Tree;

public class SparqlAstBuilder {
    public static Tree buildQueryTree() {
        Tree tree = buildTree( SparqlParser.QUERY, "QUERY" );
        tree.addChild( buildSelectClauseTree() );
        tree.addChild( buildWhereClauseTree() );

        return tree;
    }

    public static Tree buildSelectClauseTree() {
        Tree selectTree = buildTree( SparqlParser.SELECT, "SELECT" );
        selectTree.addChild( buildVariableTree( "?s" ) );

        return selectTree;
    }

    public static Tree buildWhereClauseTree() {
        Tree tree = buildTree( SparqlParser.WHERE, "WHERE" );
        tree.addChild( buildTripleTree() );

        return tree;
    }

    public static Tree buildTripleTree() {
        Tree tree = buildTree( SparqlParser.TRIPLE, "TRIPLE" );
        tree.addChild( buildVariableTree( "?s" ) );
        tree.addChild( buildVariableTree( "?p" ) );
        tree.addChild( buildVariableTree( "?o" ) );

        return tree;
    }

    public static Tree buildVariableTree( String name ) {
        return buildTree( SparqlParser.QUESTION_VARNAME, name );
    }

    public static Tree buildTree( int type ) {
        return new ComparableTree( new CommonToken( type ) );
    }

    public static Tree buildTree( int type, String text ) {
        return new ComparableTree( new CommonToken( type, text ) );
    }
}
