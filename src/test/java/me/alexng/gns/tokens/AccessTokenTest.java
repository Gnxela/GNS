package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.operators.AccessToken;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.ObjectValue;
import me.alexng.gns.util.MockFunctionToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccessTokenTest {

	@Test
	public void testGetValue() throws RuntimeException, ParsingException {
		IdentifierToken leftIdentifier = new IdentifierToken("left", FileIndex.INTERNAL_INDEX);
		IdentifierToken rightIdentifier = new IdentifierToken("right", FileIndex.INTERNAL_INDEX);
		AccessToken accessToken = new AccessToken(FileIndex.INTERNAL_INDEX);
		accessToken.bind(leftIdentifier, rightIdentifier);
		Scope globalScope = Scope.createGlobalScope(null);
		ObjectValue object = new ObjectValue(Scope.createObjectScope(globalScope), FileIndex.INTERNAL_INDEX);
		globalScope.set(leftIdentifier, object);
		object.getObjectScope().set(rightIdentifier, new NumberValue(9, FileIndex.INTERNAL_INDEX));
		assertEquals(9, accessToken.execute(globalScope).getJavaValue());
	}

	@Test
	public void testGetValue_functionCall() throws RuntimeException, ParsingException {
		IdentifierToken leftIdentifier = new IdentifierToken("left", FileIndex.INTERNAL_INDEX);
		IdentifierToken functionIdentifier = new IdentifierToken("function", FileIndex.INTERNAL_INDEX);
		AccessToken accessToken = new AccessToken(FileIndex.INTERNAL_INDEX);

		IdentifierToken argumentIdentifier = new IdentifierToken("param", FileIndex.INTERNAL_INDEX);
		ArgumentsToken argumentsToken = new ArgumentsToken(new Token[]{argumentIdentifier}, FileIndex.INTERNAL_INDEX);
		FunctionCallToken functionCallToken = new FunctionCallToken(functionIdentifier, argumentsToken, FileIndex.INTERNAL_INDEX);

		accessToken.bind(leftIdentifier, functionCallToken);

		Scope globalScope = Scope.createGlobalScope(null);
		ObjectValue object = new ObjectValue(Scope.createObjectScope(globalScope), FileIndex.INTERNAL_INDEX);
		globalScope.set(leftIdentifier, object);
		MockFunctionToken mockFunction = new MockFunctionToken(functionIdentifier, null, null, FileIndex.INTERNAL_INDEX);
		object.getObjectScope().set(functionIdentifier, mockFunction);

		Scope callingScope = globalScope.createChildScope();
		callingScope.set(argumentIdentifier, new NumberValue(7, FileIndex.INTERNAL_INDEX));
		accessToken.execute(callingScope);

		assertNotNull(mockFunction.values);
		assertEquals(1, mockFunction.values.length);
		assertTrue(mockFunction.values[0] instanceof NumberValue);
		assertEquals(7, mockFunction.values[0].getJavaValue());
	}
}
