package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.tokens.operators.AssignToken;
import me.alexng.gns.util.CountingExecutor;
import me.alexng.gns.util.ExecutableMockToken;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		assignToken.bind(identifier, new ValueToken(new NumberValue(13), FileIndex.NULL_INDEX));
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(assignToken);
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		blockToken.executeBlockWithScope(globalScope);
		assertEquals(13, globalScope.variableProvider.get(identifier).getJavaValue());
		assertEquals(13, globalScope.variableProvider.getLocal(identifier).getJavaValue());
	}

	@Test
	public void testExecute() throws RuntimeException, ParsingException {
		CountingExecutor countingExecutor = new CountingExecutor();
		IdentifierToken identifier = new IdentifierToken("test", FileIndex.NULL_INDEX);
		AssignToken assignToken = new AssignToken(FileIndex.NULL_INDEX);
		assignToken.bind(identifier, new ValueToken(new NumberValue(13), FileIndex.NULL_INDEX));
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(assignToken); // This will not increment countingExecutor
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		blockToken.executeBlock(globalScope);
		countingExecutor.assertCount(3);
		assertThrows(RuntimeException.class, () -> globalScope.variableProvider.get(identifier));
	}

	@Test
	public void testExecute_modifyParentVariable() throws RuntimeException, ParsingException {
		IdentifierToken identifier = new IdentifierToken("test", FileIndex.NULL_INDEX);
		AssignToken assignToken = new AssignToken(FileIndex.NULL_INDEX);
		assignToken.bind(identifier, new ValueToken(new NumberValue(17), FileIndex.NULL_INDEX));
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(assignToken);
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		globalScope.variableProvider.setLocal(identifier, new NumberValue(13));
		blockToken.executeBlock(globalScope);
		assertEquals(17, globalScope.variableProvider.get(identifier).getJavaValue());
	}
}
