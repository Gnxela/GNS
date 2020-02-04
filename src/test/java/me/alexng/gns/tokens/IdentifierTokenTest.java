package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.NumberValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdentifierTokenTest {

	@Test
	public void testExecute() throws RuntimeException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.variableProvider.setLocal(identifierToken, new NumberValue(13));
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_classDefined() throws RuntimeException, ParsingException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.classProvider.setLocal(identifierToken, new ClassToken(identifierToken, null, FileIndex.NULL_INDEX));
		scope.variableProvider.setLocal(identifierToken, new NumberValue(13));
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_functionDefined() throws RuntimeException, ParsingException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.functionProvider.setLocal(identifierToken, new FunctionToken(identifierToken, null, null, FileIndex.NULL_INDEX));
		scope.variableProvider.setLocal(identifierToken, new NumberValue(13));
		assertEquals(13, identifierToken.execute(scope).getJavaValue());
	}

	@Test
	public void testExecute_undefined() throws RuntimeException, ParsingException {
		IdentifierToken identifierToken = new IdentifierToken("foo", FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		assertThrows(RuntimeException.class, () -> {
			identifierToken.execute(scope);
		});
	}
}
