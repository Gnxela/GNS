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

public class ForTokenTest {

	@Test
	public void testExecute() throws RuntimeException, ParsingException {
		final String VARIABLE_NAME = "i";
		final int ITERATIONS = 7;
		final IdentifierToken identifierToken = new IdentifierToken(VARIABLE_NAME, FileIndex.INTERNAL_INDEX);

		CountingExecutor countingExecutor = new CountingExecutor();
		ExecutableMockToken mockToken = new ExecutableMockToken(countingExecutor);
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(mockToken);
		BlockToken blockToken = new BlockToken(tokens, FileIndex.INTERNAL_INDEX);

		AssignToken assignToken = new AssignToken(identifierToken, new ValueToken(new NumberValue(0), FileIndex.INTERNAL_INDEX), FileIndex.INTERNAL_INDEX);
		LessThanToken lessThanToken = new LessThanToken(identifierToken, new ValueToken(new NumberValue(ITERATIONS), FileIndex.INTERNAL_INDEX), FileIndex.INTERNAL_INDEX);
		AssignToken incrementToken = new AssignToken(identifierToken, new AdditionToken(identifierToken, new ValueToken(new NumberValue(1), FileIndex.INTERNAL_INDEX), FileIndex.INTERNAL_INDEX), FileIndex.INTERNAL_INDEX);
		Token[] arguments = new Token[]{
				assignToken,
				lessThanToken,
				incrementToken
		};
		ArgumentsToken argumentsToken = new ArgumentsToken(arguments, FileIndex.INTERNAL_INDEX);

		ForToken forToken = new ForToken(argumentsToken, blockToken, FileIndex.INTERNAL_INDEX);
		forToken.execute(Scope.createGlobalScope(null));

		countingExecutor.assertCount(ITERATIONS);
	}
}
