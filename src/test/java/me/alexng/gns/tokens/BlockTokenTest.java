package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.operators.AssignToken;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.ReturnedValue;
import me.alexng.gns.tokens.value.Value;
import me.alexng.gns.util.CountingExecutor;
import me.alexng.gns.util.ExecutableMockToken;
import me.alexng.gns.util.ReturningMockToken;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTokenTest {

	@Test
	public void testExecuteBlockWithScope() throws RuntimeException {
		CountingExecutor countingExecutor = new CountingExecutor();
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		blockToken.executeBlockWithScope(Scope.createGlobalScope(null));
		countingExecutor.assertCount(3);
	}

	@Test
	public void testExecuteBlockWithScope_modifyScope() throws RuntimeException, ParsingException {
		IdentifierToken identifier = new IdentifierToken("test", FileIndex.NULL_INDEX);
		AssignToken assignToken = new AssignToken(FileIndex.NULL_INDEX);
		assignToken.bind(identifier, new NumberValue(13, FileIndex.NULL_INDEX));
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(assignToken);
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		blockToken.executeBlockWithScope(globalScope);
		assertEquals(13, globalScope.getValue(identifier).getJavaValue());
		assertEquals(13, globalScope.getValue(identifier).getJavaValue());
	}

	@Test
	public void testExecute() throws RuntimeException, ParsingException {
		CountingExecutor countingExecutor = new CountingExecutor();
		IdentifierToken identifier = new IdentifierToken("test", FileIndex.NULL_INDEX);
		AssignToken assignToken = new AssignToken(FileIndex.NULL_INDEX);
		assignToken.bind(identifier, new NumberValue(13, FileIndex.NULL_INDEX));
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(assignToken); // This will not increment countingExecutor
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		blockToken.executeBlock(globalScope);
		countingExecutor.assertCount(3);
		assertNull(globalScope.getValue(identifier).getJavaValue());
	}

	@Test
	public void testExecute_returnValue() throws RuntimeException, ParsingException {
		CountingExecutor countingExecutor = new CountingExecutor();
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ReturningMockToken(new ReturnedValue(new NumberValue(1, FileIndex.INTERNAL_INDEX))));
		tokens.add(new ExecutableMockToken(countingExecutor)); // This token is not executed.
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		Value value = blockToken.executeBlock(globalScope);
		countingExecutor.assertCount(2);
		assertTrue(value instanceof ReturnedValue);
		assertEquals(1, ((ReturnedValue) value).getJavaValue().getJavaValue());
	}

	@Test
	public void testExecute_eolToken() throws RuntimeException, ParsingException {
		LinkedList<Token> tokens = new LinkedList<>();
		MockEOLToken mockEOLToken = new MockEOLToken();
		tokens.add(mockEOLToken);
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		blockToken.executeBlock(globalScope);
		assertFalse(mockEOLToken.executed);
	}

	@Test
	public void testExecute_modifyParentVariable() throws RuntimeException, ParsingException {
		IdentifierToken identifier = new IdentifierToken("test", FileIndex.NULL_INDEX);
		AssignToken assignToken = new AssignToken(FileIndex.NULL_INDEX);
		assignToken.bind(identifier, new NumberValue(17, FileIndex.NULL_INDEX));
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(assignToken);
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		globalScope.set(identifier, new NumberValue(13, FileIndex.NULL_INDEX));
		blockToken.executeBlock(globalScope);
		assertEquals(17, globalScope.getValue(identifier).getJavaValue());
	}

	private static class MockEOLToken extends EOLToken {

		public boolean executed = false;

		public MockEOLToken() {
			super(FileIndex.NULL_INDEX);
		}

		@Override
		public Value execute(Scope scope) throws RuntimeException {
			executed = true;
			return super.execute(scope);
		}
	}
}
