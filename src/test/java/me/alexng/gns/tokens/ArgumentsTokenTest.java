package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.util.ReturningMockToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ArgumentsTokenTest {

	@Test
	public void testGrabValues() throws RuntimeException {
		NumberValue[] numberValues = new NumberValue[]{
				new NumberValue(0, FileIndex.INTERNAL_INDEX),
				new NumberValue(1, FileIndex.INTERNAL_INDEX),
				new NumberValue(2, FileIndex.INTERNAL_INDEX),
				new NumberValue(3, FileIndex.INTERNAL_INDEX),
		};
		Token[] arguments = new Token[]{
				new ReturningMockToken(numberValues[0]),
				new ReturningMockToken(numberValues[1]),
				new ReturningMockToken(numberValues[2]),
				new ReturningMockToken(numberValues[3]),
		};
		ArgumentsToken argumentsToken = new ArgumentsToken(arguments, FileIndex.NULL_INDEX);
		assertArrayEquals(numberValues, argumentsToken.grabValues(Scope.createGlobalScope(null)));
	}

	@Test
	public void testGrabValues_usingScope() throws RuntimeException {
		NumberValue[] numberValues = new NumberValue[]{
				new NumberValue(0, FileIndex.INTERNAL_INDEX),
				new NumberValue(1, FileIndex.INTERNAL_INDEX),
				new NumberValue(2, FileIndex.INTERNAL_INDEX),
				new NumberValue(3, FileIndex.INTERNAL_INDEX),
		};
		IdentifierToken[] arguments = new IdentifierToken[]{
				new IdentifierToken("a", FileIndex.NULL_INDEX),
				new IdentifierToken("b", FileIndex.NULL_INDEX),
				new IdentifierToken("c", FileIndex.NULL_INDEX),
				new IdentifierToken("d", FileIndex.NULL_INDEX),
		};
		ArgumentsToken argumentsToken = new ArgumentsToken(arguments, FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		for (int i = 0; i < numberValues.length; i++) {
			// TODO: Same as parameters
			scope.set(arguments[i], numberValues[i]);
		}
		assertArrayEquals(numberValues, argumentsToken.grabValues(scope));
	}
}
