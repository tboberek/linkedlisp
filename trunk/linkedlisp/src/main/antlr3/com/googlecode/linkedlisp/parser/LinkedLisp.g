grammar LinkedLisp;
options {
    output=AST;
    ASTLabelType=CommonTree;
}

@header {
package com.googlecode.linkedlisp.parser;

import java.util.Map;
import java.util.HashMap;
}
@lexer::header {
package com.googlecode.linkedlisp.parser;
}

@members {
    /** List of function definitions. Must point at the FUNC nodes. */
    private Map<String,String> prefixes = new HashMap<String,String>();
    private List<CommonTree> functions = new ArrayList<CommonTree>();
    
}

eval	:	(exp)*
	;

exp	:	 '(' exp+ ')' 
	|	 var
	|	 literal
	; 

var	:	id
	|	uri
	;

id	:	ID
	;
	
uri	:	ABSOLUTE_URI 
	|	PREFIXED_URI
	;

literal	:	LITERAL 
	| 	TYPED_LITERAL 
	| 	INT 
	| 	FLOAT
	;

ID  :	('a'..'z'|'A'..'Z'|'_'|'!'|'#'..'&'|'*'..'/'|'='..'@') ('a'..'z'|'A'..'Z'|'_'|'0'..'9'|'!'|'#'..'&'|'*'..'/'|'='|'?'|'@')*
    ;

INT :	'0'..'9'+
    ;

FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

ABSOLUTE_URI	:	'<' (ID '://'~('>'|' '|')')*) '>';

PREFIXED_URI
	:	ID ':' ~('"'|' '|')')+;

COMMENT
    :   ';;' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

LITERAL
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

TYPED_LITERAL
	:	LITERAL '^^' (ABSOLUTE_URI | PREFIXED_URI);

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;

WS  : (' '|'\r'|'\n'|'\t')+ {$channel = HIDDEN;} ;

