package me.alexng.gns.gen.constructors;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.BinaryOperationToken;
import me.alexng.gns.tokens.Token;

import java.util.ListIterator;

public class BinaryOperationConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return token instanceof BinaryOperationToken && !((BinaryOperationToken) token).isBound();
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		BinaryOperationToken binaryOperation = (BinaryOperationToken) tokens.next();
		tokens.previous();
		Token left = tokens.previous();
		tokens.remove();
		tokens.next(); // Skip over the operator token
		Token right = tokens.next();
		tokens.remove();
		binaryOperation.checkOperands(left, right);
		binaryOperation.bind(left, right);
	}
}
