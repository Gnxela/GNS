package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.BooleanValue;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.util.CountingExecutor;
import me.alexng.gns.util.ExecutableMockToken;
import me.alexng.gns.util.ReturningExecutor;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IfTokenTest {

	@Test
	public void testExecute_true() throws RuntimeException {
		ReturningExecutor returningExecutor = new ReturningExecutor(BooleanValue.INTERNAL_TRUE);
		ExpressionToken expressionToken = new ExpressionToken(new ExecutableMockToken(returningExecutor), FileIndex.NULL_INDEX);
		CountingExecutor countingExecutor = new CountingExecutor();
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		IfToken ifToken = new IfToken(expressionToken, blockToken, FileIndex.NULL_INDEX);

		ifToken.execute(Scope.createGlobalScope(null));
		countingExecutor.assertCount(3);
	}

	@Test
	public void testExecute_false() throws RuntimeException {
		ReturningExecutor returningExecutor = new ReturningExecutor(BooleanValue.INTERNAL_FALSE);
		ExpressionToken expressionToken = new ExpressionToken(new ExecutableMockToken(returningExecutor), FileIndex.NULL_INDEX);
		CountingExecutor countingExecutor = new CountingExecutor();
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		IfToken ifToken = new IfToken(expressionToken, blockToken, FileIndex.NULL_INDEX);

		ifToken.execute(Scope.createGlobalScope(null));
		countingExecutor.assertCount(0);
	}

	@Test
	public void testExecute_throws() throws RuntimeException {
		ExpressionToken expressionToken = new ExpressionToken(new ExecutableMockToken(new ReturningExecutor(NullValue.INTERNAL)), FileIndex.NULL_INDEX);
		BlockToken blockToken = new BlockToken(new LinkedList<>(), FileIndex.NULL_INDEX);
		IfToken ifToken = new IfToken(expressionToken, blockToken, FileIndex.NULL_INDEX);
		assertThrows(RuntimeException.class, () -> ifToken.execute(Scope.createGlobalScope(null)));
	}
}
