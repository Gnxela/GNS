package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.NumberValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierTokenTest {

	@Test
	public void testExecute() throws RuntimeException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.set(identifierToken, new NumberValue(13, FileIndex.INTERNAL_INDEX));
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_classDefined() throws RuntimeException, ParsingException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.set(identifierToken, new TemplateToken(identifierToken, null, FileIndex.NULL_INDEX));
		scope.set(identifierToken, new NumberValue(13, FileIndex.INTERNAL_INDEX));
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_functionDefined() throws RuntimeException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.set(identifierToken, new FunctionToken(identifierToken, null, null, FileIndex.NULL_INDEX));
		scope.set(identifierToken, new NumberValue(13, FileIndex.INTERNAL_INDEX));
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_undefined() throws RuntimeException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		assertEquals(NullValue.INTERNAL, identifierToken.execute(scope));
	}
}
