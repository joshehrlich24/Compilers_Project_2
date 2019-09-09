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
        // TODO: implement this!

        return tokens;
    }

}

