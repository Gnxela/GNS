package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.tokens.operators.AdditionToken;
import me.alexng.gns.tokens.operators.AssignToken;
import me.alexng.gns.tokens.operators.LessThanToken;
import me.alexng.gns.util.CountingExecutor;
import me.alexng.gns.util.ExecutableMockToken;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class ForTokenTest {

	@Test
	public void testExecute() throws RuntimeException, ParsingException {
		CountingExecutor countingExecutor = new CountingExecutor();
		ExecutableMockToken mockToken = new ExecutableMockToken(countingExecutor);
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(mockToken);
		ForToken forToken = createForToken(0, 7, 1, tokens);
		forToken.execute(Scope.createGlobalScope(null));
		countingExecutor.assertCount(7);
	}

	@Test
	public void testExecute_conditionFalse() throws RuntimeException, ParsingException {
		CountingExecutor countingExecutor = new CountingExecutor();
		ExecutableMockToken mockToken = new ExecutableMockToken(countingExecutor);
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(mockToken);
		ForToken forToken = createForToken(1, 0, 1, tokens);
		forToken.execute(Scope.createGlobalScope(null));
		countingExecutor.assertCount(0);
	}

	@Test
	public void testExecute_negativeStart() throws RuntimeException, ParsingException {
		CountingExecutor countingExecutor = new CountingExecutor();
		ExecutableMockToken mockToken = new ExecutableMockToken(countingExecutor);
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(mockToken);
		ForToken forToken = createForToken(-2, 1, 1, tokens);
		forToken.execute(Scope.createGlobalScope(null));
		countingExecutor.assertCount(3);
	}

	@Test
	public void testExecute_negativeStartAndEnd() throws RuntimeException, ParsingException {
		CountingExecutor countingExecutor = new CountingExecutor();
		ExecutableMockToken mockToken = new ExecutableMockToken(countingExecutor);
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(mockToken);
		ForToken forToken = createForToken(-5, -3, 1, tokens);
		forToken.execute(Scope.createGlobalScope(null));
		countingExecutor.assertCount(2);
	}

	private ForToken createForToken(int start, int end, int delta, List<Token> tokens) throws ParsingException {
		final String VARIABLE_NAME = "i";
		final IdentifierToken identifierToken = new IdentifierToken(VARIABLE_NAME, FileIndex.INTERNAL_INDEX);

		BlockToken blockToken = new BlockToken(tokens, FileIndex.INTERNAL_INDEX);

		AssignToken assignToken = new AssignToken(identifierToken, new ValueToken(new NumberValue(start), FileIndex.INTERNAL_INDEX));
		LessThanToken lessThanToken = new LessThanToken(identifierToken, new ValueToken(new NumberValue(end), FileIndex.INTERNAL_INDEX));
		AssignToken incrementToken = new AssignToken(identifierToken, new AdditionToken(identifierToken, new ValueToken(new NumberValue(delta), FileIndex.INTERNAL_INDEX)));
		Token[] arguments = new Token[]{
				assignToken,
				lessThanToken,
				incrementToken
		};
		ArgumentsToken argumentsToken = new ArgumentsToken(arguments, FileIndex.INTERNAL_INDEX);

		return new ForToken(argumentsToken, blockToken, FileIndex.INTERNAL_INDEX);
	}
}
