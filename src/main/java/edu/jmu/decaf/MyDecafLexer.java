package edu.jmu.decaf;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.omg.CORBA.PRIVATE_MEMBER;

import edu.jmu.decaf.Token.Type;

/**
 * Concrete Decaf lexer class.
 */
class MyDecafLexer extends DecafLexer
{
	
	/* * * * * * * * * * * * * *
	 * For this and all future projects, you must also make sure you check the inputs
	 * to public API functions (in this case, any public members of MyDecafLexer)
	 * to make sure they aren't null or otherwise invalid
	 * * * * * * * * * * * * * */

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
    	
    	String []keywords = {"def", "if", "else", "while", "return", "break", "continue", "int", "bool", "void", "true", "false"};
    	String []reserved = {"for", "callout", "class", "interface", "extends", "implements", "new", "this", "string", "float", "double", "null"};
    	Queue<Token> tokens = new ArrayDeque<Token>();
    	
    	String str = "";
    	int lineNumber = 1;
    	StringBuffer sb = new StringBuffer();
    	Token token;
    	/*
    	 * 
    	 * \d	A digit: [0-9]
			\D	A non-digit: [^0-9]
			\s	A whitespace character: [ \t\n\x0B\f\r]
			\S	A non-whitespace character: [^\s]
			\w	A word character: [a-zA-Z_0-9]
			\W	A non-word character: [^\w]
    	 */
    	
    	  //Regex's --> 
    	Pattern whitespace = Pattern.compile("[\\s\\t\\n]+");
    	Pattern keyword = Pattern.compile("(def|if|else|while|return|break|continue|int|bool|void|true|false)"); 
        Pattern identifier = Pattern.compile("[a-zA-z][a-zA-Z0-9_]*"); 
        Pattern decimalLiteral = Pattern.compile("[0-9][1-9]*");
        Pattern hexLiteral = Pattern.compile("0x[A-F0-9]*");
        Pattern stringLiteral = Pattern.compile("\"[a-zA-Z0-9]+\"");
        Pattern symbol = Pattern.compile("\\/\\/|[(){}//[//]]|[=|<|>|!]?=|[+8\\/\\-<>!,\\;]|\\|\\||%|\\&\\&"); 
        
        //Token Patterns --> ORDER MATTERS 
        
        addTokenPattern(Token.Type.ID, identifier.toString());
        addTokenPattern(Token.Type.KEY, keyword.toString());
        addTokenPattern(Token.Type.HEX, hexLiteral.toString());
        addTokenPattern(Token.Type.DEC, decimalLiteral.toString());
        addTokenPattern(Token.Type.SYM, symbol.toString());
        addTokenPattern(Token.Type.STR, stringLiteral.toString());
        
        addIgnoredPattern(whitespace.toString());
        
        while((str = input.readLine()) != null) // Read each line of the input file one at a time
        {
        	 SourceInfo si = new SourceInfo(filename);
        	 si.lineNumber = lineNumber;
        	
        	 sb.append(str); // Do we want to add all lines to string buffer than do work or do work line by line as we read?
        	 extract(whitespace, sb);
        	
        	 while((token = nextToken(sb)) != null)
        	 {
        		 if(token.text.equals("//")) //if a token is // you know its a comment so delete the entire line therefore ignoring the comment
        		 {
        			 sb.delete(0, sb.length());
        			 continue;
        		 }
        		 token.source = si;
      
        		if(token.type == Type.ID) // Checks if an ID is a keyword or reserved word
        		{
        			for(int i = 0; i < keywords.length; i ++) // Loop over all key words, if found change the type of the token to keyword
           		 	{
        				if(keywords[i].equals(token.text))
        				{
        					token.type = Type.KEY;
        				}
           		 	}
        			
        			for(int i = 0; i < reserved.length; i ++) // Loop over all reserved words to see if it is used in program
        			{
        				if(reserved[i].equals(token.text)) // if so, throw exception
        				{
        					throw new InvalidTokenException("Use of a reserved word as an ID");
        				}
        			}
        		}
        		
        		tokens.add(new Token(token.type, token.text, token.source)); // Add the token to the queue
        		
        		extract(whitespace, sb);
        	 }
        	 if(token == null && sb.length() != 0) // if the token is null and we are not at the end of the line
        	 {
        		 throw new InvalidTokenException("Invalid token");
        	 }
        	 
        	 lineNumber++;        	
        }
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

