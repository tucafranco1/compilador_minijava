/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mjcompiler;
    //Token t = new Tplen(enumtoken.class);
// globalST.add(new STEntry(t,class",true);
/**
 *
 * @author bianca
 */
public class Parser 
{
    private Scanner scan;
//    private SymbolTable globalST;
//    private SymbolTable currentST;
      private Token lToken;
    
    public Parser(String inputFile)    
    {
        //Instancia a tabela de símbolos global e a inicializa
//         globalST = new SymbolTable<STEntry>();
//         initSymbolTable(); // coloca todas reservadas na tabela 
//     
        //Faz o ponteiro para a tabela do escopo atual apontar para a tabela global
//        currentST = globalST;
//        
        //Instancia o analisador léxico
        scan = new Scanner(/*globalST, */inputFile);
    }
    
    
    
    
    
    
    /*
     * Método que inicia o processo de análise sintática do compilador
     */
    public void execute()
    {
        advance();
        
        try
        {
            program();
        }
        catch(CompilerException e)
        {
            System.err.println(e);
        }
    }
    
    private void advance()
    {
        
        lToken = scan.nextToken();
        
        System.out.print(lToken.name + "(" + lToken.lineNumber + ")" + " " );
    }
    
    private void match(EnumToken cTokenName) throws CompilerException
    {
        if (lToken.name == cTokenName)
            advance();
        else
        {            //Erro
            throw new CompilerException("Token inesperado: " + lToken.name);
        }
    }
    
    
    /*
     * Método para o símbolo inicial da gramática
     */    
    private void program() throws CompilerException
    {
        mainClass();
        
        while (lToken.name == EnumToken.CLASS) 
            classDeclaration();
        
        match(EnumToken.EOF);
        
        System.out.println("\nCompilação encerrada com sucesso");
        
    }    
    
    
    
    private void mainClass() throws CompilerException
    {
        match(EnumToken.CLASS);
        
        match(EnumToken.ID);
        match(EnumToken.LBRACE);
        match(EnumToken.PUBLIC);
        match(EnumToken.STATIC);
        match(EnumToken.VOID);
        match(EnumToken.MAIN);
        match(EnumToken.LPARENTHESE);
        match(EnumToken.STRING);
        match(EnumToken.LBRACKET);
        match(EnumToken.RBRACKET);
        match(EnumToken.ID);
        match(EnumToken.RPARENTHESE);
        match(EnumToken.LBRACE);
        Statement();
        match(EnumToken.RBRACE);
        match(EnumToken.RBRACE);
        
        
    }
    
    private void classDeclaration() throws CompilerException
    {
        match(EnumToken.CLASS);
        match(EnumToken.ID);
        if (lToken.name == EnumToken.EXTENDS)
            advance();      
        match(EnumToken.LBRACE); // chaves
        
        while(lToken.name == EnumToken.ID || lToken.name == EnumToken.INT || lToken.name == EnumToken.BOOLEAN)
        {
            varDeclaration();
            
        }    
        
        while(lToken.name == EnumToken.PUBLIC)
        {               
            methodDeclaration();
            
        }
        match(EnumToken.RBRACE);
    }
    
    
    private void varDeclaration() throws CompilerException
    {
            Type();
            match(EnumToken.ID);
            match(EnumToken.SEMICOLON);
    }
    
    private void methodDeclaration() throws CompilerException
    {
        match(EnumToken.PUBLIC);
        Type();
        match(EnumToken.ID);
        match(EnumToken.LPARENTHESE);
        if (lToken.name == EnumToken.ID || lToken.name == EnumToken.INT || lToken.name == EnumToken.BOOLEAN) {
            Type();
            match(EnumToken.ID);
            while (lToken.name == EnumToken.COMMA) {
                match(EnumToken.COMMA);
                Type();
                match(EnumToken.ID);
            }
        }
        match(EnumToken.RPARENTHESE);
        match(EnumToken.LBRACE);
        while(lToken.name == EnumToken.ID || lToken.name == EnumToken.INT || lToken.name == EnumToken.BOOLEAN)
        {
            varDeclaration();
        }
        
        // Arrumar fatoração da gramática
        while(lToken.name == EnumToken.LBRACE || lToken.name == EnumToken.IF || lToken.name == EnumToken.WHILE
                || lToken.name == EnumToken.SOPRINTLN || lToken.name == EnumToken.ID)
        {
            Statement();
        }
        
        match(EnumToken.RETURN);
        Expression();
        match(EnumToken.SEMICOLON);
        match(EnumToken.RBRACE);
    }
    
    
    private void Type() throws CompilerException
    {
        if(lToken.name == EnumToken.INT)
            {
                advance();
                if(lToken.name == EnumToken.LBRACKET) // colchete
                {
                    match(EnumToken.LBRACKET);
                    match(EnumToken.RBRACKET);
                }
                
            }   
        else if(lToken.name == EnumToken.BOOLEAN || lToken.name == EnumToken.ID)
            advance();
        else
        {            //Erro
            throw new CompilerException("Token inesperado: " + lToken.name);
        }
    }
    
    private void Statement() throws CompilerException {
        switch (lToken.name) {
            case LBRACE:
                match(EnumToken.LBRACE);
                while (lToken.name == EnumToken.LBRACE || lToken.name == EnumToken.IF || lToken.name == EnumToken.WHILE
                        || lToken.name == EnumToken.SOPRINTLN || lToken.name == EnumToken.ID) {
                    Statement();
                }
                match(EnumToken.RBRACE);
                break;
            case IF:
                match(EnumToken.IF);
                match(EnumToken.LPARENTHESE);
                Expression();
                match(EnumToken.RPARENTHESE);
                Statement();
                match(EnumToken.ELSE);
                Statement();
                break;
            case WHILE:
                match(EnumToken.LPARENTHESE);
                Expression();
                match(EnumToken.RPARENTHESE);
                Statement();
                break;
            case SOPRINTLN:
                match(EnumToken.LPARENTHESE);
                Expression();
                match(EnumToken.RPARENTHESE);
                match(EnumToken.SEMICOLON);
                break;
            case ID:
                match(EnumToken.ID);
                ID_();
                break;
            default:
                throw new CompilerException("Token inesperado: " + lToken.name);
                
        }
    }

          private void ID_() throws CompilerException
    {
        if (lToken.name == EnumToken.EQ) {
            advance();
            Expression();
            match(EnumToken.SEMICOLON);
        }
        else if (lToken.name == EnumToken.LBRACKET) {
            advance();
            Expression();
            match(EnumToken.RBRACKET);
            match(EnumToken.EQ);
            Expression();
            match(EnumToken.SEMICOLON);
        }
        
    }

     
      private void Expression() throws CompilerException
    {
        if (lToken.name == (EnumToken.INTEGER_LITERAL)
                || lToken.name == (EnumToken.TRUE)
                || lToken.name == (EnumToken.FALSE)
                || lToken.name == (EnumToken.ID)
                || lToken.name == (EnumToken.THIS)) {
            advance();
            Expression_();
        }
        else if(lToken.name == EnumToken.NOT){
            advance();
            Expression();
            Expression_();
        }
        else if(lToken.name == EnumToken.LPARENTHESE){
            advance();
            Expression();
            match(EnumToken.RPARENTHESE);
            Expression_(); 
        }
        else if(lToken.name == EnumToken.NEW){
            advance();
            New_();
        }
        else if(lToken.name == EnumToken.LBRACKET){
            advance();
            Expression();
            match(EnumToken.RBRACKET);
            Expression_();
        }
        else if(lToken.name == EnumToken.DOT){
            advance();
            Dot();
        }
        else if(lToken.name == EnumToken.AND||
                lToken.name == EnumToken.GT||
                lToken.name == EnumToken.LT||
                lToken.name == EnumToken.EQ|| //não seeei
                lToken.name == EnumToken.NE||
                lToken.name == EnumToken.PLUS||
                lToken.name == EnumToken.MINUS||
                lToken.name == EnumToken.MULT||
                lToken.name == EnumToken.DIV){
            Op();
            Expression();
            Expression_();
        }
    }
       private void New_() throws CompilerException
    {
        if(lToken.name == EnumToken.INT){
            advance();
            match(EnumToken.LBRACKET);
            Expression();
            match(EnumToken.RBRACKET);
            Expression_();
        }
        else if(lToken.name == EnumToken.ID){
            advance();
            match(EnumToken.LPARENTHESE);
            match(EnumToken.RPARENTHESE);
        }
        
    }
      private void Expression_() throws CompilerException
    {
        if (lToken.name == (EnumToken.INTEGER_LITERAL)||
                    lToken.name == (EnumToken.TRUE)||
                    lToken.name == (EnumToken.FALSE)||
                    lToken.name == (EnumToken.ID)||
                    lToken.name == (EnumToken.THIS)||
                    lToken.name == (EnumToken.NOT)||
                    lToken.name == (EnumToken.LPARENTHESE)||
                    lToken.name == (EnumToken.NEW) ) {
            Expression();
            Expression_();
        }
        else{
        }
        
    }
       private void Dot() throws CompilerException
    {
        if(lToken.name == (EnumToken.LENGTH)){
            advance();
            Expression_();
        }
        else if(lToken.name == (EnumToken.ID)){ //not é '!'????
            advance();
            match(EnumToken.LPARENTHESE);
            if(lToken.name == (EnumToken.INTEGER_LITERAL)||
                    lToken.name == (EnumToken.TRUE)||
                    lToken.name == (EnumToken.FALSE)||
                    lToken.name == (EnumToken.ID)||
                    lToken.name == (EnumToken.THIS)||
                    lToken.name == (EnumToken.NOT)||
                    lToken.name == (EnumToken.LPARENTHESE)||
                    lToken.name == (EnumToken.NEW)  ){
                Expression();
                while(lToken.name == (EnumToken.COMMA)){
                    advance();
                    Expression();
                }
            match(EnumToken.RPARENTHESE);
            Expression_();
            }
        }
    }
    private void Op() throws CompilerException
    {
        switch (lToken.name) {
            case AND:
                advance();
                break;
            case GT:
                advance();            
                break;
            case LT:
                advance();
                break;
            case EQ:
                advance();
                break;
            case NE:
                advance();
                break;
            case PLUS:
                advance();
                break;
            case MINUS:
                advance();
                break;
            case MULT:
                advance();
                break;
            case DIV:
                advance();
                break;
            default:
                throw new CompilerException("Token inesperado: " + lToken.name);
        } 
    }
}
 // em todos novos escopos declarar uma nova tabela de símbolos passando o pai.
// currentST = new SymbolTable<STEntry>(currentST);





















