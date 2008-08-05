grammar StateMachine;

options {
  output=AST;
  k=3;
  //ASTLabelType = MfTree;
}

tokens {
  EVENT_LIST; EVENT; COMMAND_LIST; COMMAND; STATE; TRANSITION_LIST; TRANSITION; ACTION_LIST; RESET_EVENT_LIST;
}

@header {
  package statemachine;

  import java.util.HashMap;
}

@lexer::header {
  package statemachine;
}


@members {
  /** Map variable name to Integer object holding value */
  HashMap memory = new HashMap();
}

// primitives

fragment LETTER  : ('a'..'z' | 'A'..'Z' | '_');
fragment DIGIT  : ('0'..'9');
fragment NEWLINE:'\r'? '\n' ;

ID      : LETTER (LETTER | DIGIT)* ;

WHITE_SPACE   : (' ' |'\t' | NEWLINE)+ {skip();} ;
COMMENT   : '#' ~'\n'* '\n' {skip();};

INT :   '0'..'9'+ ;

// simple types

event   :   n=ID c=ID -> ^(EVENT $n $c);

command  : ID ID -> ^(COMMAND ID+);

transition  : ID '=>' ID -> ^(TRANSITION ID+);

// containers

eventList 
  :  'events' event* 'end' -> ^(EVENT_LIST event*);

commandList  : 'commands' command* 'end' -> ^(COMMAND_LIST command*);

actionList  : 'commands' '{' ID* '}' -> ID*;

resetEventList  :  'resetEvents' ID* 'end' -> ^(RESET_EVENT_LIST ID*);

state  
  : 'state' ID actionList? transition* 'end' 
    -> ^(STATE ID ^(ACTION_LIST actionList?) ^(TRANSITION_LIST transition*) )
  ;

machine  : eventList resetEventList? commandList? state*;


