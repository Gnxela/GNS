package me.alexng.gns.gen.constructors;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.operators.BinaryOperatorToken;

import java.util.ListIterator;

public class BinaryOperationConstructor implements Constructor {

	private Class<? extends BinaryOperatorToken<?, ?>>[] operators;
	private boolean leftToRight;

	public BinaryOperationConstructor(boolean leftToRight, Class<? extends BinaryOperatorToken<?, ?>>... operators) {
		this.leftToRight = leftToRight;
		this.operators = operators;
	}

	@Override
	public boolean isLeftToRight() {
		return leftToRight;
	}

	@Override
	public boolean accepts(Token token) {
		for (Class<? extends BinaryOperatorToken<?, ?>> clazz : operators) {
			if (clazz.isAssignableFrom(token.getClass()) && !((BinaryOperatorToken<?, ?>) token).isBound()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		BinaryOperatorToken binaryOperation;
		if (leftToRight) {
			binaryOperation = Assembler.castTo(BinaryOperatorToken.class, tokens.next());
			tokens.previous();
		} else {
			binaryOperation = Assembler.castTo(BinaryOperatorToken.class, tokens.previous());
		}
		for (Class<? extends BinaryOperatorToken<?, ?>> clazz : operators) {
			if (clazz.isAssignableFrom(binaryOperation.getClass()) && !binaryOperation.isBound()) {
				Token left = tokens.previous();
				tokens.remove();
				tokens.next(); // Skip over the operator token
				Token right = tokens.next();
				tokens.remove();
				binaryOperation.checkOperands(left, right);
				binaryOperation.bind(left, right);
				return;
			}
		}
	}
}
