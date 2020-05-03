package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.tokens.operators.AdditionToken;
import me.alexng.gns.tokens.operators.AssignToken;
import me.alexng.gns.tokens.operators.LessThanToken;
import me.alexng.gns.util.CountingExecutor;
import me.alexng.gns.util.ExecutableMockToken;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class WhileTokenTest {

	@Test
	public void testExecute() throws RuntimeException, ParsingException {
		final String VARIABLE_NAME = "i";
		final IdentifierToken identifierToken = new IdentifierToken(VARIABLE_NAME, FileIndex.INTERNAL_INDEX);
		CountingExecutor countingExecutor = new CountingExecutor();
		ExecutableMockToken mockToken = new ExecutableMockToken(countingExecutor);
		AssignToken incrementToken = new AssignToken(identifierToken, new AdditionToken(identifierToken, new ValueToken(new NumberValue(1), FileIndex.INTERNAL_INDEX)));
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(mockToken);
		tokens.add(incrementToken);
		BlockToken blockToken = new BlockToken(tokens, FileIndex.INTERNAL_INDEX);
		LessThanToken lessThanToken = new LessThanToken(identifierToken, new ValueToken(new NumberValue(9), FileIndex.INTERNAL_INDEX));
		WhileToken whileToken = new WhileToken(new ExpressionToken(lessThanToken, FileIndex.INTERNAL_INDEX), blockToken, FileIndex.INTERNAL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		scope.set(identifierToken, new NumberValue(0).wrap());
		whileToken.execute(scope);
		countingExecutor.assertCount(9);
	}
}
