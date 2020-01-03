package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.tokens.BindableToken;
import me.alexng.gns.tokens.Token;

public abstract class OperatorToken extends BindableToken {

	public OperatorToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	/**
	 * Returns a string representing the operator, does not include operands.
	 * For example: Plus -> "+"
	 */
	public abstract String getOperatorString();

	/**
	 * Returns an array of operands.
	 * For example, a binary operation will return {left, right}.
	 * This array will be supplied to {@link me.alexng.gns.tokens.OperatorFunctionToken} to evaluate Object operations.
	 */
	public abstract Token[] getOperands();
}
