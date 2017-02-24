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
public class Token 
{
    public EnumToken name; // nome geral
    public EnumToken attribute; // atributo do nome, ex: operador de MENOS
    public String value; // conteúdo do token
    public int lineNumber; // linha do token
    
    //public STEntry tsPtr;
    
    public Token(EnumToken name)
    {
        this.name = name;
        attribute = EnumToken.UNDEF;
        
        //tsPtr = null;
    }
    
    public Token(EnumToken name, EnumToken attr)
    {
        this.name = name;
        attribute = attr;
        //tsPtr = null;
    }
}
