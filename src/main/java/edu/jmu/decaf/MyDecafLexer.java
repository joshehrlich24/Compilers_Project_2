package edu.jmu.decaf;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Concrete Decaf lexer class.
 */
class MyDecafLexer extends DecafLexer
{

    /**
     * Perform lexical analysis, converting Decaf source code into a stream of
     * {@link Token} objects.
     *
     * @param input Input text reader
     * @param filename Source file name (for source info)
     * @return Queue of lexed tokens
     * @throws IOException Thrown if there is an error reading from the input
     * @throws InvalidTokenException Thrown if an invalid token is encountered
     */
    @Override
    public Queue<Token> lex(BufferedReader input, String filename)
            throws IOException, InvalidTokenException
    {
        Queue<Token> tokens = new ArrayDeque<Token>();
        System.out.println("Were in MyDecafLexer");
        
        System.out.println(tokens.size());
        
       String str = input.readLine();
       System.out.println(str);
        
        while(str != null)
        {
        	//Do stuff
        	
        	str = input.readLine();
        	System.out.println(str);
        }
       
       
        
        
      
        
        //Regex's --> 
        Pattern identifier = Pattern.compile("[a-zA-z][a-zA-Z_0-9]*");
        Pattern keyword = Pattern.compile("[a-z][a-z0-9]*"); // keywords don't have _ or numbers but it says they can?
        Pattern decimalLiteral = Pattern.compile("[0-9][1-9]");
        Pattern hexLiteral = Pattern.compile("0x([a-zA-z]*)|([0-9]*)");
        Pattern stringLiteral = Pattern.compile("");
        Pattern symbol = Pattern.compile("[-\\[\\]\\(\\{\\}\\)\\,\\;\\=\\+\\-\\*\\/\\%\\<\\>\\<=\\>=\\==\\!=\\&&\\||\\!]"); // will this allow  <, <=, and = in the same regex without confusion(prob not)
        
        Matcher m1 = identifier.matcher("");
        Matcher m2 = keyword.matcher("");
        Matcher m3 = decimalLiteral.matcher("");
        Matcher m4 = hexLiteral.matcher("");
        Matcher m5 = stringLiteral.matcher("");
        Matcher m6 = symbol.matcher("");
        
        this.addTokenPattern(Token.Type.ID, identifier.toString());
        this.addTokenPattern(Token.Type.KEY, keyword.toString());
        this.addTokenPattern(Token.Type.DEC, decimalLiteral.toString());
        this.addTokenPattern(Token.Type.HEX, hexLiteral.toString());
        this.addTokenPattern(Token.Type.STR, stringLiteral.toString());
        this.addTokenPattern(Token.Type.SYM, symbol.toString());
  
        // TODO: implement this!

        return tokens;
    }
    
    /*
     *  Another approach (PA2):
	 *  – Build a DFA for each regex
	 *	*******– Run each DFA in sequence in priority order on input until there is
	 *	no outgoing edge on a character*******
	 *	● If current state is accepting, generate token and restart
	 *	● Otherwise, run the next DFA (if no more DFAs, report error)
     * 
     */

}

