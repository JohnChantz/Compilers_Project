/**
 *  This code is part of the lab exercises for the Compilers course
 *  at Harokopio University of Athens, Dept. of Informatics and Telematics.
 */

import static java.lang.System.out;

%%

%class Lexer
%unicode
%public
%final
%integer
%line
%column

%{
    // user custom code

    StringBuffer sb = new StringBuffer();

%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]
Comment        = "/*" [^*] ~"*/" | "/*" "*"+ "/"

Identifier     = [:jletter:] [:jletterdigit:]*
IntegerLiteral = 0 | [1-9][0-9]*
Exponent       = [eE][\+\-]?[0-9]+
Float1         = [0-9]+ \. [0-9]+ {Exponent}?
Float2         = \. [0-9]+ {Exponent}?
Float3         = [0-9]+ \. {Exponent}?
Float4         = [0-9]+ {Exponent}
FloatLiteral   = {Float1} | {Float2} | {Float3} | {Float4}

%state STRING

%%

<YYINITIAL> {
    /* reserved keywords */
    "number"                       {out.println("NUMBER");}
    "string"                       {out.println("STRING");}
    "while"                        {out.println("WHILE");}
    "if"                           {out.println("IF");}
    "else"                         {out.println("ELSE");}
    "write"                        {out.println("WRITE");}
    "void"                         {out.println("VOID");}
    "return"                       {out.println("RETURN");}
    "BREAK"                        {out.println("BREAK");}
    "continue"                     {out.println("CONTINUE");}
    "class"                        {out.println("CLASS");}
    "new"                          {out.println("NEW");}
    "null"                         {out.println("NULL");}
    "this"                         {out.println("THIS");}

    /* identifiers */
    {Identifier}                   { out.println("id:" + yytext()); }

    /* literals */
    {IntegerLiteral}               { out.println("integer:" + yytext()); }

    /* Floats */
    {FloatLiteral}                 {out.println("float:" + Double.parseDouble(yytext()));}

    \"                             { sb.setLength(0); yybegin(STRING); }

    /* operators */
      "="                            {out.println("ASSIGN");}
      "+"                            {out.println("PLUS");}
      "-"                            {out.println("MINUS");}
      "*"                            {out.println("Multiply");}
      "/"                            {out.println("DIV");}
      "%"                            {out.println("MOD");}
      ">"                            {out.println("GREATER-THAN");}
      "<"                            {out.println("LESS-THAN");}
      "<="                           {out.println("LESS-THAN-EQUAL");}
      ">="                           {out.println("GREATER-THAN-EQUAL");}
      "=="                           {out.println("EQUALS");}
      "!="                           {out.println("NOT-EQUAL");}
      "."                            {out.println("DOT");}
      "&&"                           {out.println("AND");}
      "||"                           {out.println("OR");}
      "!"                            {out.println("EXCLAMATION MARK");}

      /*separators*/
      ";"                            {out.println("SEMICOLON");}
      "("                            {out.println("LEFT-PARENTHESIS");}
      ")"                            {out.println("RIGHT-PARENTHESIS");}
      "{"                            {out.println("LEFT-BRACKET");}
      "}"                            {out.println("RIGHT-BRACKET");}
      ","                            {out.println("COMMA");}

    /* comments */
    {Comment}                      { /* ignore */ }

    /* whitespace */
    {WhiteSpace}                   { /* ignore */ }
}

<STRING> {
    \"                             { yybegin(YYINITIAL);
                                     out.println("string:" + sb.toString());
                                   }

    [^\n\r\"\\]+                   { sb.append(yytext()); }
    \\t                            { sb.append('\t'); }
    \\n                            { sb.append('\n'); }

    \\r                            { sb.append('\r'); }
    \\\"                           { sb.append('\"'); }
    \\                             { sb.append('\\'); }
}

/* error fallback */
[^]                                { throw new RuntimeException((yyline+1) + ":" + (yycolumn+1) + ": illegal character <"+ yytext()+">"); }
