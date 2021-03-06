import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;

/**
 * Code to test Project 5; you should definitely add more tests!
 */
public class ExpressionParserPartialTester {
	private ExpressionParser _parser;

	@Before
	/**
	 * Instantiates the actors and movies graphs
	 */
	public void setUp () throws IOException {
		_parser = new SimpleExpressionParser();
	}

	@Test
	/**
	 * Just verifies that the SimpleExpressionParser could be instantiated without crashing.
	 */
	public void finishedLoading () {
		assertTrue(true);
		// Yay! We didn't crash
	}

	@Test
	public void testDeepCopy() throws ExpressionParseException {
		final String expressionStr = "a+b";
		Expression exp = _parser.parse(expressionStr, false);
		Expression exp2 = exp.deepCopy();
		assertNotEquals(exp, exp2); // ensure that the two expressions have different memory addresses
		assertEquals(exp.convertToString(0), exp2.convertToString(0));
	}

	@Test
	public void testDeepCopy2() throws ExpressionParseException {
		final String expressionStr = "(x+(x)+(x+x)+x)";
		Expression exp = _parser.parse(expressionStr, false);
		Expression exp2 = exp.deepCopy();
		assertNotEquals(exp, exp2); // ensure that the two expressions have different memory addresses
		assertEquals(exp.convertToString(0), exp2.convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpressionA () throws ExpressionParseException {
		final String expressionStr = "10*x*z+2*(15+y)";
		final String parseTreeStr = "+\n\t*\n\t\t10\n\t\tx\n\t\tz\n\t*\n\t\t2\n\t\t()\n\t\t\t+\n\t\t\t\t15\n\t\t\t\ty\n";
		assertEquals(parseTreeStr.replace('*','·'), _parser.parse(expressionStr, false).convertToString(0).replace('*','·'));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression1 () throws ExpressionParseException {
		final String expressionStr = "a+b";
		final String parseTreeStr = "+\n\ta\n\tb\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression2 () throws ExpressionParseException {
		final String expressionStr = "13*x";
		final String parseTreeStr = "·\n\t13\n\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression3 () throws ExpressionParseException {
		final String expressionStr = "4*(z+5*x)";
		final String parseTreeStr = "·\n\t4\n\t()\n\t\t+\n\t\t\tz\n\t\t\t·\n\t\t\t\t5\n\t\t\t\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}
    
    @Test
    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    public void testExpression4 () throws ExpressionParseException {
        final String expressionStr = "2+x+y+z+3";
        final String parseTreeStr = "+\n\t2\n\tx\n\ty\n\tz\n\t3\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
    }
    
    @Test
    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    public void testExpression5 () throws ExpressionParseException {
        final String expressionStr = "2*x+3*y+4*z+(7+6*z)";
        final String parseTreeStr = "+\n\t·\n\t\t2\n\t\tx\n\t·\n\t\t3\n\t\ty\n\t·\n\t\t4\n\t\tz\n\t()\n\t\t+\n\t\t\t7\n\t\t\t·\n\t\t\t\t6\n\t\t\t\tz\n";
        System.out.println(_parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
        System.out.println(parseTreeStr);
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
    }
    
    @Test
    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    public void testExpression6 () throws ExpressionParseException {
        final String expressionStr = "7*x+4*y+3*z";
        final String parseTreeStr = "+\n\t·\n\t\t7\n\t\tx\n\t·\n\t\t4\n\t\ty\n\t·\n\t\t3\n\t\tz\n";
        System.out.println(_parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
        System.out.println(parseTreeStr);
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
    }

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpressionAndFlatten1 () throws ExpressionParseException {
		final String expressionStr = "1+2+3";
		final String parseTreeStr = "+\n\t1\n\t2\n\t3\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	public void testParens() throws ExpressionParseException {
		final String expressionStr = "1+((2+3))";
		final String parseTreeStr = "+\n\t1\n\t()\n\t\t()\n\t\t\t+\n\t\t\t\t2\n\t\t\t\t3\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	public void testParens2() throws ExpressionParseException {
		final String expressionStr = "((a+b))";
		final String parseTreeStr = "()\n\t()\n\t\t+\n\t\t\ta\n\t\t\tb\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	public void testParens3() throws ExpressionParseException {
		final String expressionStr = "(a+b)";
		final String parseTreeStr = "()\n\t+\n\t\ta\n\t\tb\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	public void testParens4() throws ExpressionParseException {
		final String expressionStr = "(30000006+(a+b))";
		final String parseTreeStr = "()\n\t+\n\t\t30000006\n\t\t()\n\t\t\t+\n\t\t\t\ta\n\t\t\t\tb\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpressionAndFlatten2 () throws ExpressionParseException {
		final String expressionStr = "(x+(x)+(x+x)+x)";
		final String parseTreeStr = "()\n\t+\n\t\tx\n\t\t()\n\t\t\tx\n\t\t()\n\t\t\t+\n\t\t\t\tx\n\t\t\t\tx\n\t\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpressionAndFlatten3 () throws ExpressionParseException {
		final String expressionStr = "2";
		final String parseTreeStr = "2\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpressionAndFlatten4 () throws ExpressionParseException {
		final String expressionStr = "(2)";
		final String parseTreeStr = "()\n\t2\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0).replace('*', '·'));
	}

	@Test(expected = ExpressionParseException.class) 
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException1 () throws ExpressionParseException {
		final String expressionStr = "1+2+";
		_parser.parse(expressionStr, false);
	}

	@Test(expected = ExpressionParseException.class) 
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException2 () throws ExpressionParseException {
		final String expressionStr = "((()))";
		_parser.parse(expressionStr, false);
	}

	@Test(expected = ExpressionParseException.class) 
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException3 () throws ExpressionParseException {
		final String expressionStr = "()()";
		_parser.parse(expressionStr, false);
	}

	@Test(expected = ExpressionParseException.class)
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException4 () throws ExpressionParseException {
		final String expressionStr = "2*+3";
		_parser.parse(expressionStr, false);
	}

	@Test(expected = ExpressionParseException.class)
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException5 () throws ExpressionParseException {
		final String expressionStr = "2++3";
		_parser.parse(expressionStr, false);
	}

	@Test(expected = ExpressionParseException.class)
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException6 () throws ExpressionParseException {
		final String expressionStr = "2-3";
		_parser.parse(expressionStr, false);
	}

	@Test(expected = ExpressionParseException.class)
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException7 () throws ExpressionParseException {
		final String expressionStr = "(2+3";
		_parser.parse(expressionStr, false);
	}
}
