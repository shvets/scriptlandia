tree grammar SparqlWalker;
options {
    tokenVocab=Sparql; 
    ASTLabelType=CommonTree;
    output=template;
} 

@header {
    package com.devx.sparql;
}

query
    : ^( QUERY selectClause whereClause ) -> query( selectClause={$selectClause.st}, whereClause={$whereClause.st} )
    ;

selectClause
    :  ^( SELECT e=element ) -> selectClause( element={$e.st} )
    ;

whereClause
    : ^( WHERE triples+=triple+ ) -> whereClause( triples={$triples} )
    ;

triple
    : ^( TRIPLE s=element p=element o=element ) -> triple( subject={$s.st}, predicate={$p.st}, object={$o.st} )
    ;

element
    : QUESTION_VARNAME -> template( name = {$QUESTION_VARNAME.text} ) "$name$"
    | DOLLAR_VARNAME -> template( name = {$DOLLAR_VARNAME.text} ) "$name$"
    ;
