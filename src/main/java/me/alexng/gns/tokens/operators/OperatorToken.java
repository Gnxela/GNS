package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.IdentifiedToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.value.Value;

public abstract class OperatorToken extends IdentifiedToken {

	private boolean bound = false;

	public OperatorToken(String operatorString, FileIndex fileIndex) {
		super(new IdentifierToken(operatorString, fileIndex), fileIndex);
	}

	/**
	 * Returns an array of operands.
	 * For example, a binary operation will return {left, right}.
	 * This array will be supplied to {@link me.alexng.gns.tokens.OperatorFunctionToken} to evaluate Object operations.
	 */
	public abstract Value[] getOperands(Scope scope) throws RuntimeException;

	/**
	 * Must be called when an operator binds t
	 */
	public void bind() {
		this.bound = true;
	}

	public boolean isBound() {
		return bound;
	}

}
