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
 *               | ID = Expression ;
 *               | ID [ Expression ] = Expression ;
 * 8. Expression → INTEGER_LITERAL Exp'
 *               | true            Exp'
 *               | false           Exp'
 *               | ID              Exp'
 *               | this            Exp'
 *               | new int [ Expression ]  Exp'
 *               | new ID ( )              Exp'
 *               | ! Expression            Exp'
 *               | ( Expression )          Exp'
 *
 * 9. Exp' → Op Expression Exp'
 *               | [Expression] Exp'
 *               | . DOT
 *
 * 10. DOT → lenght Exp'
 *               | ID ( (Expression ( , Expression )* )? ) Exp'
 *
 * 11. Op → && | < | > | == | != | + | - | * | /
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
        /*globalST = new SymbolTable<STEntry>();
        initSymbolTable();
        */
        Scanner scanner = new Scanner(/*globalST, */"teste1.mj"); // instanciação
        
        Token tok;
        
        //double var = 2.e+10;
        
        do
        {
            tok = scanner.nextToken();
            // parser.scan = (scanner.nextToken());
            //System.out.printf("Chamei ");
            System.out.print(tok.name + " " + tok.value +"\n");
        } while (tok.name != EnumToken.EOF);
     
    }

}
