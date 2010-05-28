grammar LinkedLisp;

@header {
package com.googlecode.linkedlisp.parser;

import java.util.Map;
import java.util.HashMap;
import com.googlecode.linkedlisp.*;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
}
@lexer::header {
package com.googlecode.linkedlisp.parser;

import com.googlecode.linkedlisp.*;

}

@members {
	private OntModel model = ModelFactory.createOntologyModel();
}

eval returns [Expression expr]
	:    e=exp {expr=e;}
	;

exp returns [Expression exp]
    :    lst=listExp {exp = lst;}
    |    qe=quoteExp {exp = qe;}
	|	 v=var {exp = v;}
	|	 l=literal {exp = l;}
	; 

quoteExp returns [ListExpression list]
    :   '\'' e=exp {list = new ListExpression(); list.append(new IDExpression("QUOTE")); list.append(e);}
    ;
    
listExp returns [ListExpression list]
    :   {$list = new ListExpression();}
        '(' (e=exp {list.append(e);})* ')' 
    ;
var	returns [Expression exp]
    :	i=id {exp = i;}
	|	u=uri {exp = u;}
	;

id	returns [IDExpression idexp]
    :	ID {idexp = new IDExpression($ID.text);}
	;
	
uri	returns [ResourceExpression expr]
    :	'<' URI '>' {expr = new ResourceExpression(model.createResource($URI.text));}
	|	URI {expr = new ResourceExpression($URI.text);}
	;

literal returns [Literal lit]
    :	s=str {lit = new Literal(s);} 
	| 	s=str '^^' u=uri {lit = new TypedLiteral(s, u);}
	| 	INT {lit = new IntLiteral(Long.parseLong($INT.text));}
	| 	FLOAT {lit = new FloatLiteral(Long.parseLong($FLOAT.text));}
	|   NIL {lit = null;}
	;

str returns [String s]
    :   LITERAL {s = $LITERAL.text;}
    ;

NIL :   'nil'
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

URI
	:	ID ':' ~('"'|' '|')'|'>')+;

COMMENT
    :   ';;' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

LITERAL
    :  '"' (( ESC_SEQ | ~('\\'|'"') )*) '"' 
       {setText(getText().substring(1, getText().length()-1));}
    ;

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

