/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mjcompiler;

/**
 *
 * @author bianca
 */
public enum EnumToken 
{
    UNDEF,
    CLASS, //
    PUBLIC,//
    STATIC,//
    VOID,//
    MAIN,//
    ID,
    IF,//
    WHILE,//
    SOPRINTLN,
    THIS,//
    STRING,
    INT,//
    BOOLEAN,//
    NEW,//
    EXTENDS,//
    LBRACKET, // colchetes
    RBRACKET,
    LPARENTHESE,
    RPARENTHESE,
    LBRACE, // chaves
    RBRACE,
    PERIOD,
    DOT,
    COMMA,// virgula
    SEMICOLON, /// ponto e vírgula
    ELSE,//
    ATTRIB,  // atribuição
    NOT,
    ARITHOP, // operador aritimético
    PLUS,   // soma
    MINUS,  // subtração
    MULT,   // multiplicação
    DIV,    // divisão
    RELOP,  // operador relacional
    EQ,     // equal 
    NE,     // not equal
    GT,    // greater than
    LT,     // lower than
    SEP,   // separadores
    NUMBER,
    INTEGER_LITERAL,
    TRUE,//
    FALSE,//
    LOGOP,
    AND,
    RETURN,//
    LENGTH,
    EOF
}
