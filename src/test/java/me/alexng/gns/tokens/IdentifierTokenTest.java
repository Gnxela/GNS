package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierTokenTest {

	@Test
	public void testExecute() throws RuntimeException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.set(identifierToken, new NumberValue(13).wrap());
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_classDefined() throws RuntimeException, ParsingException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.set(identifierToken, new ClassToken(identifierToken, null, FileIndex.NULL_INDEX));
		scope.set(identifierToken, new NumberValue(13).wrap());
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_functionDefined() throws RuntimeException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.set(identifierToken, new FunctionToken(identifierToken, null, null, FileIndex.NULL_INDEX));
		scope.set(identifierToken, new NumberValue(13).wrap());
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_undefined() throws RuntimeException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		assertEquals(Value.NULL, identifierToken.execute(scope));
	}
}
