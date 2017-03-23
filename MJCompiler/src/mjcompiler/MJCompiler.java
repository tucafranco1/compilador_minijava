/* Alunos:
 * Mateus Franco
 * Murilo Blanco Flor
 *------------------------------------------------------------------------------
 * Gramática LL(1)
 * 1. Program → MainClass ( ClassDeclaration )*
 ∗
 * 2. MainClass → class ID { public static void main (String[ ] ID){ Statement } }
 *
 * 3. ClassDeclaration → class ID ( extends ID )? { ( VarDeclaration )∗ ( MethodDeclaration)∗ }
 *
 * 4. VarDeclaration → Type ID ;
 *
 * 5. MethodDeclaration → public Type ID ( ( Type ID ( , Type ID )∗ )? ) { ( VarDeclaration)∗ ( Statement )∗ return Expression ; }
 *
 * 6. Type → int[] | boolean | int | ID
 *
 * 7. Statement → { ( Statement )∗ }
 *               | if ( Expression ) Statement else Statement
 *               | while ( Expression ) Statement
 *               | System.out.println ( Expression ) ;
 *               | ID ID'
 * 8. ID'  →  = Expression ;
 *          | [ Expression ] = Expression ;
 *
 * 9. Expression → INTEGER_LITERAL Exp'
 *               | true            Exp'
 *               | false           Exp'
 *               | ID              Exp'
 *               | this            Exp'
 *               | ! Expression            Exp'
 *               | ( Expression )          Exp'
 *               | new New'
 * 
 * 10. New' →    int [ Expression ]  Exp'
 *               | ID ( )            Exp'
 *
 * 11. Exp' → Op Expression Exp'
 *               | [Expression] Exp'        tem erro aqui!!!!%%%%%%%%%
 *               | . DOT
 *
 * 12. DOT → lenght Exp'
 *               | ID ( (Expression ( , Expression )* )? ) Exp'
 *
 * 13. Op → && | < | > | == | != | + | - | * | /
 */

package mjcompiler;

/**
 *
 * @author Bianca
 */
public class MJCompiler 
{
    public static void main(String[] args) 
    {
        NewJFrame j = new NewJFrame();
        j.setVisible(true);
        
        
        
        //PARSER => colocar no botao COMPILAR
        Parser parser = new Parser("teste1.mj");
        parser.execute();
        
        
        //Scanner scanner = new Scanner(globalST, "teste1.mj"); // instanciação
        
        Token tok;
        
        //double var = 2.e+10;
        
//        do
//        {
//            tok = scanner.nextToken();
//           
//            System.out.print(tok.name + " " + "Line Number:" + tok.lineNumber + " " + tok.value +"\n");
//        } while (tok.name != EnumToken.EOF);
//     
    }

}
