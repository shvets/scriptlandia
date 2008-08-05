/**
 * Grammar for RDF SPARQL, described in http://www.w3.org/TR/rdf-sparql-query/#grammar .
 */
grammar Sparql;
options {
    output = AST;
    k = 1;
}
tokens {
    QUERY;
    SELECT;
    WHERE;
    TRIPLE;
}

@header {
    package com.devx.sparql;
}

@lexer::header {
    package com.devx.sparql;
}

/* Bombing on first error now.  TODO: collect errors, recover... */
@members {
    protected void mismatch( IntStream input, int tokenType, BitSet follow ) throws RecognitionException {
        throw new MismatchedTokenException( tokenType, input );
    }
    
    public void recoverFromMismatchedSet( IntStream input, RecognitionException ex, BitSet follow )
        throws RecognitionException {
        
        throw ex;
    }
}

@rulecatch {
    catch ( RecognitionException ex ) {
        throw ex;
    }
}

/* A.8, rule 1 */
query : prologue ( selectQuery | constructQuery | describeQuery | askQuery ) -> ^( QUERY prologue? selectQuery? constructQuery? describeQuery? askQuery? )
    ;

/* A.8, rule 2 */
prologue : baseDecl? prefixDecl*
    ;

/* A.8, rule 3 */
baseDecl : 'BASE' IRI_REFERENCE
    ;

/* A.8, rule 4 */
prefixDecl : 'PREFIX' PNAME_NAMESPACE IRI_REFERENCE
    ;

/* A.8, rule 5 */
selectQuery : 'SELECT' ( 'DISTINCT' | 'REDUCED' )? ( variable+ | '*' ) datasetClause* whereClause solutionModifier -> ^( SELECT variable+ ) whereClause
    ;

/* A.8, rule 6 */
constructQuery : 'CONSTRUCT' constructTemplate datasetClause* whereClause solutionModifier
    ;

/* A.8, rule 7 */
describeQuery : 'DESCRIBE' ( variableOrIriReference+ | '*' ) datasetClause* whereClause? solutionModifier
    ;

/* A.8, rule 8 */
askQuery : 'ASK' datasetClause* whereClause
    ;

/* A.8, rule 9 */
datasetClause : 'FROM' ( defaultGraphClause | namedGraphClause )
    ;

/* A.8, rule 10 */
defaultGraphClause : sourceSelector
    ;

/* A.8, rule 11 */
namedGraphClause : 'NAMED' sourceSelector
    ;

/* A.8, rule 12 */
sourceSelector : iriReference
    ;

/* A.8, rule 13 */
whereClause : 'WHERE'? groupGraphPattern -> ^( WHERE groupGraphPattern )
    ;

/* A.8, rule 14 */
solutionModifier : orderClause? limitOffsetClauses?
    ;

/* A.8, rule 15 */
limitOffsetClauses : limitClause offsetClause?
    | offsetClause limitClause?
    ;
	
/* A.8, rule 16 */
orderClause : 'ORDER' 'BY' orderCondition+
    ;

/* A.8, rule 17 */
orderCondition : ( ( 'ASC' | 'DESC' ) brackettedExpression )
	| ( constraint | variable )
	;

/* A.8, rule 18 */
limitClause : 'LIMIT' INTEGER
    ;

/* A.8, rule 19 */
offsetClause : 'OFFSET' INTEGER
    ;

/* A.8, rule 20 */
groupGraphPattern
	:	'{' triplesBlock? ( ( graphPatternNotTriples | filter ) '.'? triplesBlock? )* '}' -> triplesBlock*
	;

/* A.8, rule 21 */
triplesBlock : triplesSameSubject ( '.' triplesBlock? )? -> triplesSameSubject triplesBlock?
    ;

/* A.8, rule 22 */
graphPatternNotTriples : optionalGraphPattern
    | groupOrUnionGraphPattern
    | graphGraphPattern
    ;

/* A.8, rule 23 */
optionalGraphPattern : 'OPTIONAL' groupGraphPattern -> groupGraphPattern
	;

/* A.8, rule 24 */
graphGraphPattern : 'GRAPH' variableOrIriReference groupGraphPattern
    ;

/* A.8, rule 25 */
groupOrUnionGraphPattern : groupGraphPattern ( 'UNION' groupGraphPattern )* -> groupGraphPattern*
    ;

/* A.8, rule 26 */
filter : 'FILTER' constraint
    ;

/* A.8, rule 27 */
constraint : brackettedExpression
    | builtInCall
    | functionCall
	;

/* A.8, rule 28 */
functionCall : iriReference argList
    ;

/* A.8, rule 29 */
argList : ( NIL | '(' expression ( ',' expression )* ')' )
    ;

/* A.8, rule 30 */
constructTemplate : '{' constructTriples? '}'
    ;

/* A.8, rule 31 */
constructTriples : triplesSameSubject ( '.' constructTriples? )?
    ;

/* A.8, rule 32 */
triplesSameSubject 
    : variableOrTerm propertyListNotEmpty -> ^( TRIPLE variableOrTerm propertyListNotEmpty )
    | triplesNode propertyList -> ^( TRIPLE triplesNode propertyList )
    ;

/* A.8, rule 33 */
propertyListNotEmpty : verb objectList ( ';' ( verb objectList )? )*
    ;

/* A.8, rule 34 */
propertyList : propertyListNotEmpty?
    ;

/* A.8, rule 35 */
objectList : object ( ',' object )*
    ;

/* A.8, rule 36 */
object : graphNode
    ;

/* A.8, rule 37 */
verb : variableOrIriReference
    | 'a'
    ;

/* A.8, rule 38 */
triplesNode : collection
    | blankNodePropertyList
    ;

/* A.8, rule 39 */
blankNodePropertyList : '[' propertyListNotEmpty ']'
    ;

/* A.8, rule 40 */
collection : '(' graphNode+ ')'
    ;

/* A.8, rule 41 */
graphNode : variableOrTerm
    | triplesNode
    ;

/* A.8, rule 42 */
variableOrTerm : variable
    | graphTerm
    ;

/* A.8, rule 43 */
variableOrIriReference : variable
    | iriReference
    ;
	
/* A.8, rule 44 */
variable : QUESTION_VARNAME
    | DOLLAR_VARNAME
    ;

/* A.8, rule 45 */
graphTerm : iriReference
    | rdfLiteral
    | numericLiteral
    | booleanLiteral
    | blankNode
    | NIL
    ;

/* A.8, rule 46 */
expression : conditionalOrExpression
    ;

/* A.8, rule 47 */
conditionalOrExpression : conditionalAndExpression ( '||' conditionalAndExpression )*
    ;

/* A.8, rule 48 */
conditionalAndExpression : valueLogical ( '&&' valueLogical )*
    ;

/* A.8, rule 49 */
valueLogical : relationalExpression
    ;

/* A.8, rule 50 */
relationalExpression : numericExpression
    ( '=' numericExpression
      | '!=' numericExpression
      | '<' numericExpression
      | '>' numericExpression
      | '<=' numericExpression
      | '>=' numericExpression
    )?
    ;

/* A.8, rule 51 */
numericExpression : additiveExpression
    ;

/* A.8, rule 52 */
additiveExpression : multiplicativeExpression
    ( '+' multiplicativeExpression
      | '-' multiplicativeExpression
      | numericLiteralPositive
      | numericLiteralNegative
    )*
    ;
    
/* A.8, rule 53 */
multiplicativeExpression : unaryExpression
    ( '*' unaryExpression
      | '/' unaryExpression
    )*
    ;

/* A.8, rule 54 */
unaryExpression : '!' primaryExpression
    | '+' primaryExpression
    | '-' primaryExpression
    | primaryExpression
    ;

/* A.8, rule 55 */
primaryExpression : brackettedExpression
    | builtInCall
    | iriReferenceOrFunction
    | rdfLiteral
    | numericLiteral
    | booleanLiteral
    | variable
    ;

/* A.8, rule 56 */
brackettedExpression : '(' expression ')'
    ;

/* A.8, rule 57 */
builtInCall : 'STR' '(' expression ')'
    | 'LANG' '(' expression ')'
    | 'LANGMATCHES' '(' expression ',' expression ')'
    | 'DATATYPE' '(' expression ')'
    | 'BOUND' '(' variable ')'
    | 'sameTerm' '(' expression ',' expression ')'
    | 'isIRI' '(' expression ')'
    | 'isURI' '(' expression ')'
    | 'isBLANK' '(' expression ')'
    | 'isLITERAL' '(' expression ')'
    | regexExpression
    ;

/* A.8, rule 58 */
regexExpression : 'REGEX' '(' expression ',' expression ( ',' expression )? ')'
    ;

/* A.8, rule 59 */
iriReferenceOrFunction : iriReference argList?
    ;

/* A.8, rule 60 */
rdfLiteral : string ( LANGUAGE_TAG | ( '^^' iriReference ) )?
    ;

/* A.8, rule 61 */
numericLiteral : numericLiteralUnsigned
    | numericLiteralPositive
    | numericLiteralNegative
    ;

/* A.8, rule 62 */
numericLiteralUnsigned : INTEGER
    | DECIMAL
    | DOUBLE
    ;

/* A.8, rule 63 */
numericLiteralPositive : POSITIVE_INTEGER
    | POSITIVE_DECIMAL
    | POSITIVE_DOUBLE
    ;

/* A.8, rule 64 */
numericLiteralNegative : NEGATIVE_INTEGER
    | NEGATIVE_DECIMAL
    | NEGATIVE_DOUBLE
    ;

/* A.8, rule 65 */
booleanLiteral : 'true'
    | 'false'
    ;

/* A.8, rule 66 */
string : SINGLE_QUOTED_STRING
    | DOUBLE_QUOTED_STRING
    | LONG_SINGLE_QUOTED_STRING
    | LONG_DOUBLE_QUOTED_STRING
    ;

/* A.8, rule 67 */
iriReference : IRI_REFERENCE
    | prefixedName
    ;
    
/* A.8, rule 68 */
prefixedName : PNAME_LOCAL_NAME
    | PNAME_NAMESPACE
    ;

/* A.8, rule 69 */
blankNode : BLANK_NODE_LABEL
    | ANONYMOUS
    ;

/* A.8, rule 70 */
IRI_REFERENCE : '<' ~( '<' | '>' | '"' | '{' | '}' | '|' | '^' | '`' | '\\' | '\u0000' .. '\u0020' )* '>'
    ;

/* A.8, rule 71 */
PNAME_NAMESPACE : PNAME_PREFIX? ':'
    ;

/* A.8, rule 72 */
PNAME_LOCAL_NAME : PNAME_NAMESPACE PNAME_LOCAL
    ;

/* A.8, rule 73 */
BLANK_NODE_LABEL : '_:' PNAME_LOCAL
    ;

/* A.8, rule 74 */
QUESTION_VARNAME : '?' VARNAME
    ;

/* A.8, rule 75 */
DOLLAR_VARNAME : '$' VARNAME
    ;

/* A.8, rule 76 */
LANGUAGE_TAG : '@' ( 'a' .. 'z' | 'A' .. 'Z' )+ ( '-' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ )*
    ;

/* A.8, rule 77 */
INTEGER : ( '0' .. '9' )+
    ;

/* A.8, rule 78 */
DECIMAL : ( '0' .. '9' )+ '.' ( '0' .. '9' )*
    | '.' ( '0' .. '9' )+
    ;

/* A.8, rule 79 */
DOUBLE : ( '0' .. '9' )+ '.' ( '0' .. '9' )* EXPONENT
    | '.' ( '0' .. '9' )+ EXPONENT
    | ( '0' .. '9' )+ EXPONENT
    ;

/* A.8, rule 80 */
POSITIVE_INTEGER : '+' INTEGER
    ;

/* A.8, rule 81 */
POSITIVE_DECIMAL : '+' DECIMAL
    ;

/* A.8, rule 82 */
POSITIVE_DOUBLE : '+' DOUBLE
    ;

/* A.8, rule 83 */
NEGATIVE_INTEGER : '-' INTEGER
    ;

/* A.8, rule 84 */
NEGATIVE_DECIMAL : '-' DECIMAL
    ;

/* A.8, rule 85 */
NEGATIVE_DOUBLE : '-' DOUBLE
    ;

/* A.8, rule 86 */
EXPONENT : ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
    ;

/* A.8, rule 89 */
LONG_SINGLE_QUOTED_STRING : '\'\'\'' ( ( '\'' | '\'\'' )? ( ~( '\'' | '\\' ) | ESCAPE_CHARACTER ) )* '\'\'\''
    ;	

/* A.8, rule 90 */
LONG_DOUBLE_QUOTED_STRING : '"""' ( ( '"' | '""' )? ( ~( '"' | '\\' ) | ESCAPE_CHARACTER ) )* '"""'
    ;	

/* A.8. rule 87 */
SINGLE_QUOTED_STRING : '\'' ( ~( '\u0027' | '\u005C' | '\u000A' ) | ESCAPE_CHARACTER )* '\''
    ;

/* A.8. rule 88 */
DOUBLE_QUOTED_STRING : '"' ( ~( '\u0022' | '\u005C' | '\u000A' ) | ESCAPE_CHARACTER )* '"'
    ;

/* A.8, rule 91 */
ESCAPE_CHARACTER : '\\' ( 't' | 'b' | 'n' | 'r' | 'f' | '\\' | '"' | '\'' )
    ;

/* A.8, rule 92 */
NIL : '(' WHITESPACE* ')'
    ;

/* A.8, rule 93 */
WHITESPACE : ( '\u0020' | '\u0009' | '\u000A' ) { $channel = HIDDEN; }
    ;

/* A.8, rule 94 */
ANONYMOUS : '[' WHITESPACE* ']'
    ;

/* A.8, rule 97 */
VARNAME : ( PNAME_CHARS_PLUS_UNDERSCORE | '0' .. '9' )
    ( PNAME_CHARS_PLUS_UNDERSCORE
      | '0' .. '9'
      | '\u00B7'
      | '\u0300' .. '\u036F'
      | '\u203F' .. '\u2040' )*
      ;

/* A.8, rule 99 */
PNAME_PREFIX : PNAME_CHARS_BASE ( ( PNAME_CHARS | '.' )* PNAME_CHARS )?
    ;

/* A.8, rule 100 */
PNAME_LOCAL : ( PNAME_CHARS_PLUS_UNDERSCORE | '0' .. '9' ) ( ( PNAME_CHARS | '.' )* PNAME_CHARS )?
    ;

/* A.8, rule 98 */
PNAME_CHARS : ( PNAME_CHARS_PLUS_UNDERSCORE
    | '-'
    | '0' .. '9'
    | '\u00B7'
    | '\u0300' .. '\u036F'
    | '\u203F' .. '\u2040'
    )
    ;

/* A.8, rule 96 */
PNAME_CHARS_PLUS_UNDERSCORE : ( PNAME_CHARS_BASE | '_' )
    ;

/* A.8, rule 95 */
PNAME_CHARS_BASE : ( 'A' .. 'Z'
    | 'a' .. 'z'
    | '\u00C0' .. '\u00D6'
    | '\u00D8' .. '\u00F6'
    | '\u00F8' .. '\u02FF'
    | '\u0370' .. '\u037D'
    | '\u037F' .. '\u1FFF'
    | '\u200C' .. '\u200D'
    | '\u2070' .. '\u218F'
    | '\u2C00' .. '\u2FEF'
    | '\u3001' .. '\uD7FF'
    | '\uF900' .. '\uFDCF'
    | '\uFDF0' .. '\uFFFD'
    )
    ;
