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
	
//	String[] keyWordArray = {"def","if", "else", 
//		"while", "return", "break", "continue", "int", "bool", "void", "true", "false"};

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
    	
    	String str = "";
    	int lineNumber = 1;
    	StringBuffer sb = new StringBuffer();
    	Token token;
    	//StringBuffer temp = new StringBuffer();
    	
    	
    	
    	
    	  //Regex's --> 
        Pattern identifier = Pattern.compile("[a-zA-z][a-zA-Z_0-9]*"); //Cant start with an _ --> Correct???
        Pattern whitespace = Pattern.compile("([\\s+])|(\\t)");
        Pattern keyword = Pattern.compile("def|if|else|while|return|break|continue|int|bool|void|true|false"); // Can keywords have _ --> ???? This may violate the rules for keywords
        Pattern decimalLiteral = Pattern.compile("[0-9][1-9]*");
        Pattern hexLiteral = Pattern.compile("0x[A-F0-9]*");
        Pattern stringLiteral = Pattern.compile("\"[a-zA-Z0-9]*\"");
        Pattern symbol = Pattern.compile("[-\\[\\]\\(\\{\\}\\)\\,\\;\\=\\+\\-\\*\\/\\%\\<\\>\\<=\\>=\\==\\!=\\&&\\||\\!]"); // will this allow  <, <=, and = in the same regex without confusion(prob not)
       
        //Token Patterns --> ORDER MATTERS --> This order will probably need to be rearranged once tested
        addTokenPattern(Token.Type.KEY, keyword.toString());
        addTokenPattern(Token.Type.ID, identifier.toString());
        addTokenPattern(Token.Type.HEX, hexLiteral.toString());
        addTokenPattern(Token.Type.DEC, decimalLiteral.toString());
        addTokenPattern(Token.Type.SYM, symbol.toString());
        addTokenPattern(Token.Type.STR, stringLiteral.toString());
        
      
        //Token token = new Token(this.nextToken(sb).type, sb.toString(), si);
       
        //Ignored Patterns
       
        
        while((str = input.readLine()) != null)
        {
        	 
        	 extract(whitespace, sb);
        	 sb.append(str); // Do we want to add all lines to string buffer than do work or do work line by line as we read?
        	 while((token = nextToken(sb)) != null)
        	 {
        		 extract(whitespace, sb);
        		 SourceInfo si = new SourceInfo(filename, lineNumber); //does this need to be a full path? with file extension(.decaf)? Yes
        		 token.source = si;
        		 tokens.add(new Token(token.type, token.text, token.source));
        		 
        		 
        	 }
        	lineNumber++;
        }
        //System.out.println("End Loop");
       System.out.println("Tokens -->" + tokens.size());
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

