grammar LinkedLisp;

@header {
package com.googlecode.linkedlisp.parser;

import java.util.Map;
import java.util.HashMap;
import com.googlecode.linkedlisp.*;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
}
@lexer::header {
package com.googlecode.linkedlisp.parser;

import com.googlecode.linkedlisp.*;
import java.util.Properties;
import java.io.*;

}

@members {
	private OntModel model = ModelFactory.createOntologyModel();
}

@lexer::members {

	public static String escape(String s) {
        //return s.replaceAll("\\n","\t").replaceAll("\\t","\t");
        // This is broken WRT leading and trailing spaces.
        Properties prop = new Properties();
        try {
            prop.load(new ByteArrayInputStream(("X=:|:" + s+":|:").getBytes("utf8")));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String value = prop.getProperty("X");
        return value.substring(3,value.length()-3);
    }
}

eval returns [Object expr]
	:    e=exp {expr=e;} EOF
	;

exp returns [Object exp]
    :    lst=listExp {exp = lst;}
    |    qe=quoteExp {exp = qe;}
	|	 v=var {exp = v;}
	|	 l=literal {exp = l;}
	; 

quoteExp returns [ListExpression list]
    :   '\'' e=exp {list = new ListExpression(); list.append(new Symbol("QUOTE")); list.append(e);}
    ;
    
listExp returns [ListExpression list]
    :   {$list = new ListExpression();}
        '(' (e=exp {list.append(e);})* ')' 
    ;
var	returns [Object exp]
    :	i=id {exp = i;}
	|	u=uri {exp = u;}
	;

id	returns [Symbol idexp]
    :	ID {idexp = new Symbol($ID.text);}
	;
	
uri	returns [java.net.URI expr]
    :	'<' URI '>' {try {
        	expr = new java.net.URI($URI.text);
        } catch (java.net.URISyntaxException e) {
        	System.err.println(e.getMessage());
        	System.exit(1);
        }
    }
	|	URI {try {
            expr = new java.net.URI($URI.text);
        } catch (java.net.URISyntaxException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
	;

literal returns [Object lit]
    :	s=str {lit = s;} 
	| 	s=str '^^' u=uri {
            TypeMapper mapper = TypeMapper.getInstance();
            RDFDatatype datatype = mapper.getTypeByName(model.expandPrefix(u.toString()));
            lit = model.createTypedLiteral(s,datatype).getValue();
		}
	| 	INT {lit = Long.parseLong($INT.text);}
	| 	FLOAT {lit = Double.parseDouble($FLOAT.text);}
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
       {setText(escape(getText().substring(1, getText().length()-1)));}
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

