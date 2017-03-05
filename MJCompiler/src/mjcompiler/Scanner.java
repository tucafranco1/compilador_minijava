package mjcompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.StringCharacterIterator;

/**
 *
 * @author Murilo Blanco Flor
 */
public class Scanner {

    private static String input;
    private StringCharacterIterator inputIt, InputIt;
    private SymbolTable st;
    private int lineNumber, tam = 0;
    

    public Scanner(/*SymbolTable globalST, */String inputFileName) {
        File inputFile = new File(inputFileName);
        //st = globalST;

        try {
            FileReader fr = new FileReader(inputFile);

            int size = (int) inputFile.length();
            char[] buffer = new char[size];

            fr.read(buffer, 0, size);

            input = new String(buffer);

            inputIt = new StringCharacterIterator(input);
            
            lineNumber = 1;
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado");
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo");
        }
    }

    public Token nextToken() {

        Token tok = new Token(EnumToken.UNDEF);
        int begin = 0, end = 0;

        String lexema = "";
        char ch = inputIt.current();
       
        while (true) {

            //Consome espaços em branco e volta para o estado inicial
            if (inputIt.current() == ' ') {
                inputIt.next();

                //System.out.println("espaco em branco\n");
            } else if (inputIt.current() == 10) // pula linha - \n
            {
                lineNumber++;
                inputIt.next();

            } else if (inputIt.current() == 9) // espaço de tabulação \t
            {
                inputIt.next();

            } else if (inputIt.current() == 13)// Retorna início da linha \r
            {
                inputIt.next();

            } else if (inputIt.current() == 12)// formuario \f
            {
                inputIt.next();

            }

            // Ignora comentarios
            if (inputIt.current() == '/') {
                inputIt.next();
                if (inputIt.current() == '*') {

                    System.out.println("ignora comentário de página");

                    inputIt.next();
                    while (true) {

                        if (inputIt.current() == 10) {

                            lineNumber++;
                        }

                        if (inputIt.current() == '*') {
                            inputIt.next();

                            if (inputIt.current() == '/') {

                                inputIt.next();

                                break;
                            }

                        }
                        inputIt.next();

                    }
                    System.out.println("acabou comentário de página");
                } else if (inputIt.current() == '/') {
                    System.out.println("ignora comentário de linha");
                    inputIt.next();

                    while (inputIt.current() != 10 && (inputIt.getIndex() != inputIt.getEndIndex())) {

                        inputIt.next();
                       // System.out.printf("LOOP%c", inputIt.current());
                    }

                    System.out.printf("acabou o comentário de linha %c", inputIt.current());
                   // lineNumber++;
                    // inputIt.next();

                } else {
                    // System.out.println("entrei onde devia");
                    tok.name = EnumToken.ARITHOP;
                    tok.attribute = EnumToken.DIV;
                    tok.value = "/";
                    return tok;
                }
            }
//            //Operadores aritméticos
            if (inputIt.current() == '+' || inputIt.current() == '-'
                    || inputIt.current() == '*' || inputIt.current() == '/'
                    || inputIt.current() == '%') {
                
                tok.name = EnumToken.ARITHOP;

                switch (inputIt.current()) {
                    case '+':
                        tok.attribute = EnumToken.PLUS;
                        tok.value = "+";
                        break;
                    case '-':
                        tok.attribute = EnumToken.MINUS;
                        tok.value = "-";
                        break;
                    case '*':
                        tok.attribute = EnumToken.MULT;
                        tok.value = "*";
                        break;
                    case '/':
                        tok.attribute = EnumToken.DIV;
                        tok.value = "/";
                        break;
                }
                inputIt.next();
                

                tok.lineNumber = lineNumber;

                return tok;
            }

            // OPERADORES LÓGICOS {&&}
            if (inputIt.current() == '&') {
               
                inputIt.next();
                if (inputIt.current() == '&') {
                    tok.name = EnumToken.LOGOP;
                    inputIt.next();
                    tok.value = "&&";
                    tok.lineNumber = lineNumber;
                    tok.lineNumber = lineNumber;
                    return tok;
                } else {
                    tok.name = EnumToken.UNDEF;
                    tok.lineNumber = lineNumber;
                    tok.value = "&";
                    inputIt.next();
                    return tok;
                }
            }

            // OPERADOR DE COMPARAÇÂO { < > != = e !}
            if (inputIt.current() == '<' || inputIt.current() == '>'
                    || inputIt.current() == '!' || inputIt.current() == '=') {
                tok.name = EnumToken.RELOP;
                begin = inputIt.getIndex();

                switch (inputIt.current()) {
                    case '<':
                        tok.attribute = EnumToken.LT;
                        tok.value = "<";
                        inputIt.next();
                        break;
                    case '>':
                        tok.attribute = EnumToken.GT;
                        tok.value = ">";                             
                        inputIt.next();
                        break;
                    case '!':
                        inputIt.next();
                        if (inputIt.current() == '=') {
                            tok.attribute = EnumToken.NE;
                            tok.value = "!=";
                            inputIt.next();
                        } else {
                            tok.name = EnumToken.NOT;
                            tok.value = "!"; 
                           inputIt.next();
                            // System.out.printf("atual: %d",inputIt.getIndex());
                            //  System.out.printf("%c",inputIt.current());
                        }
                        break;
                    case '=':
                        inputIt.next();
                        if (inputIt.current() == '=') {
                            tok.attribute = EnumToken.EQ;
                            tok.value = "==";
                            inputIt.next();
                        } else {
                            tok.name = EnumToken.ATTRIB;
                            tok.value = "=";
                            inputIt.next();
                        }
                        break;

                }

                // System.out.printf("\n\n%c",inputIt.current());
                tok.lineNumber = lineNumber;

                return tok;
            }

            /// SEPARADORES
            if (inputIt.current() == '(' || inputIt.current() == ')'
                    || inputIt.current() == '[' || inputIt.current() == ']'
                    || inputIt.current() == '{' || inputIt.current() == '}'
                    || inputIt.current() == ';' || inputIt.current() == '.' || inputIt.current() == ',') {
                tok.name = EnumToken.SEP;
                switch (inputIt.current()) {
                    case ',':
                        tok.value = ",";
                        tok.attribute = EnumToken.COMMA;
                        inputIt.next();
                        break;
                    case '.':
                        tok.attribute = EnumToken.DOT;
                        tok.value = ".";
                        inputIt.next();
                        break;
                    case ';':
                        tok.attribute = EnumToken.SEMICOLON;
                        tok.value = ";";
                        inputIt.next();
                        break;
                    case '(':
                        tok.attribute = EnumToken.LPARENTHESE;
                        tok.value = "(";
                        inputIt.next();
                        break;
                    case ')':
                        tok.attribute = EnumToken.RPARENTHESE;
                        tok.value = ")";
                        inputIt.next();
                        break;
                    case '{':
                        tok.attribute = EnumToken.LBRACE;
                        tok.value = "{";
                        inputIt.next();
                        break;
                    case '}':
                        tok.attribute = EnumToken.RBRACE;
                        tok.value = "}";
                        inputIt.next();
                        break;
                    case '[':
                        tok.attribute = EnumToken.LBRACKET;
                        tok.value = "[";
                        inputIt.next();
                        break;
                    case ']':
                        tok.attribute = EnumToken.RBRACKET;
                        tok.value = "]";
                        inputIt.next();
                        break;

                }

                // System.out.printf("\n\n%c",inputIt.current());
                tok.lineNumber = lineNumber;

                return tok;
            }

            //            boolean, class, else, extends, false, if, int, length,
            //main, new, public, return, static, String, System.out.println, this, true,
            //void e while;
            // RESERVEDS
            // ID or RESERVEDS WORDS
            if (Character.isLetter(inputIt.current())) {
                begin = inputIt.getIndex();
                // System.out.printf("ENTREI MANO\n");
                int q0 = 0, q1 = 1, q2 = 2, qf = 3, estado = q1;
                //              lexema = lexema + inputIt.current();
                while (true) {
                    while (true) {
                        switch (estado) {
                            case 0:
                                inputIt.next();

                                if (Character.isLetter(inputIt.current())) {
                                    estado = q1;
                                }
                                break;
                            case 1:
                                // lexema = new String(input.substring(begin,end));
                                inputIt.next();
//                                lexema = lexema + inputIt.current();
                                // System.out.printf("dentro do if -> %c atual: %d end: %d \n",inputIt.current(),inputIt.getIndex(), inputIt.getEndIndex());//System.out.printf("%c ",inputIt.current());
                                if (Character.isLetter(inputIt.current()) || Character.isDigit(inputIt.current()) || inputIt.current() == '_') {
                                    estado = q2;
                                } else {
                                    end = inputIt.getIndex();
                                    lexema = new String(input.substring(begin, end));
                                    tok.name = EnumToken.ID;
                                    tok.value = lexema;
                                    tok.lineNumber = lineNumber;
                                    return tok;
                                }

                                break;
                            case 2:
                                inputIt.next();
                                
                                if (Character.isLetter(inputIt.current()) || Character.isDigit(inputIt.current()) || inputIt.current() == '_') {
                                    estado = q2;
                                    //lexema = lexema + inputIt.current();
                                } else {
                                    estado = qf;
                                }
                                break;
                            case 3:
                                end = inputIt.getIndex();
                                lexema = new String(input.substring(begin, end));
                                tok.name = EnumToken.ID;
                                tok.value = lexema;
                                tok.lineNumber = lineNumber;
                                switch (lexema) {
                                    case "boolean":
                                        tok.name = EnumToken.BOOLEAN;
                                        return tok;
                                    case "class":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "PUBLIC":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "STATIC":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "VOID":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "MAIN":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "IF":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "WHILE":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "THIS":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "INT":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "NEW":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "EXTENDS":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "INT":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "ELSE":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "TRUE":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "FALSE":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    case "RETURN":
                                        tok.name = EnumToken.CLASS;
                                        return tok;
                                    default:
                                        tok.name = EnumToken.ID;
                                        return tok;
                                }
                                

                        }

                    }
                }
            }

            // INTEGER_LITERAL
            if (Character.isDigit(inputIt.current())) {
                lexema = lexema + inputIt.current();
                while (Character.isDigit(inputIt.current())) {
                    inputIt.next();
                    lexema = lexema + inputIt.current();

                }
                tok.name = EnumToken.INTEGER_LITERAL;
                tok.lineNumber = lineNumber;
                tok.value = lexema;
                return tok;

            }

            //System.out.printf("atual: %d end: %d \n", inputIt.getIndex(), inputIt.getEndIndex());
            if (inputIt.getIndex() == inputIt.getEndIndex()) {
                // System.out.printf("tudo certo");
                tok.name = EnumToken.EOF;
                return tok;
            }

            //Continua....
        }//nextToken
    }
}
