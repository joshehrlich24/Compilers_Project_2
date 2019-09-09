package edu.jmu.decaf;

import java.io.*;
import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for lexer
 */
public class TestLexer extends TestCase
{
    /**
     * Initialization
     *
     * @param testName name of the test case
     */
    public TestLexer(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(TestLexer.class);
    }

    /**
     * Lex a single token and verify that it has the correct type and contents
     * @param text Decaf source code
     * @param expectedType Correct token type
     * @param expectedText Correct token contents
     */
    public static void verifySingleToken(String text,
            Token.Type expectedType, String expectedText)
    {
        try {
            Queue<Token> tokens = (new MyDecafLexer()).lex(text);
            assertEquals(1, tokens.size());
            assertEquals(expectedType, tokens.peek().type);
            assertEquals(expectedText, tokens.peek().text);
        } catch (IOException ex) {
            assertTrue(false);
        } catch (InvalidTokenException ex) {
            assertTrue(false);
        }
    }

    /**
     * Lex multiple tokens and verify that the correct number of tokens were
     * found.
     * @param text Decaf source code
     * @param expectedNumTokens Correct number of tokens
     */
    public static void verifyMultipleTokens(String text, int expectedNumTokens)
    {
        try {
            Queue<Token> tokens = (new MyDecafLexer()).lex(text);
            assertEquals(expectedNumTokens, tokens.size());
        } catch (IOException ex) {
            assertTrue(false);
        } catch (InvalidTokenException ex) {
            assertTrue(false);
        }
    }

    /**
     * Lex multiple tokens and verify that the tokens match the expected types.
     * @param text Decaf source code
     * @param expectedTypes Correct token types, in sequence
     */
    public static void verifyMultipleTokens(String text, Token.Type expectedTypes[])
    {
        try {
            Queue<Token> tokens = (new MyDecafLexer()).lex(text);
            assertEquals(expectedTypes.length, tokens.size());
            for (int i=0; i<expectedTypes.length; i++) {
                assertEquals(expectedTypes[i], tokens.poll().type);
            }
        } catch (IOException ex) {
            assertTrue(false);
        } catch (InvalidTokenException ex) {
            assertTrue(false);
        }
    }

    /**
     * Make sure the lexer throws an exception for an invalid token.
     * @param text Invalid Decaf source code
     */
    public static void verifyInvalidToken(String text)
    {
        try {
            (new MyDecafLexer()).lex(text);
            assertTrue(false);
        } catch (IOException ex) {
            assertTrue(false);
        } catch (InvalidTokenException ex) {
            assertTrue(true);
        }
    }

    /**
     * Make sure the lexer does not throw an exception for an empty string.
     */
    public static void testEmpty()
    {
        try {
            assertTrue((new MyDecafLexer()).lex("").size() == 0);
        } catch (IOException ex) {
            assertTrue(false);
        } catch (InvalidTokenException ex) {
            assertTrue(false);
        }
    }

    public void testSingleLetterID() { verifySingleToken("a",   Token.Type.ID, "a"); }
    public void testMultiLetterID()  { verifySingleToken("abc", Token.Type.ID, "abc"); }
    public void testSingleDigit()    { verifySingleToken("1",   Token.Type.DEC, "1"); }
    public void testKeyword()        { verifySingleToken("def", Token.Type.KEY, "def"); }
    public void testKeywordID()      { verifySingleToken("int3",   Token.Type.ID, "int3"); }
    public void testSymbol()         { verifySingleToken("+",      Token.Type.SYM, "+"); }
    public void testString()         { verifySingleToken("\"hi\"", Token.Type.STR, "\"hi\""); }

    public void testComments()       { verifyMultipleTokens("// test", 0); }
    public void testMultiTokens()    { verifyMultipleTokens("def foo", 2); }
    public void testInvalidToken()   { verifyInvalidToken("@"); }

}
