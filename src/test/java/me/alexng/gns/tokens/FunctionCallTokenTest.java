package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.util.MockFunctionToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionCallTokenTest {

	@Test
	public void testExecute() throws RuntimeException {
		IdentifierToken functionIdentifier = new IdentifierToken("function", FileIndex.INTERNAL_INDEX);
		IdentifierToken argumentIdentifier = new IdentifierToken("param", FileIndex.INTERNAL_INDEX);
		ArgumentsToken argumentsToken = new ArgumentsToken(new Token[]{argumentIdentifier}, FileIndex.INTERNAL_INDEX);
		FunctionCallToken functionCallToken = new FunctionCallToken(functionIdentifier, argumentsToken, FileIndex.INTERNAL_INDEX);
		MockFunctionToken mockFunction = new MockFunctionToken(functionIdentifier, null, null, FileIndex.INTERNAL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		Scope scope = Scope.createObjectScope(globalScope);
		scope.set(functionIdentifier, mockFunction);
		scope.set(argumentIdentifier, new NumberValue(7, FileIndex.INTERNAL_INDEX));
		functionCallToken.execute(scope);
		assertNotNull(mockFunction.values);
		assertEquals(1, mockFunction.values.length);
		assertTrue(mockFunction.values[0] instanceof NumberValue);
		assertEquals(7, mockFunction.values[0].getJavaValue());
	}
}
