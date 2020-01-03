package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.tokens.BindableToken;

public abstract class OperatorToken extends BindableToken {

	public OperatorToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	// TODO: Better name

	/**
	 * @return true if the execution was forwarded to an object. False otherwise.
	 */
	public boolean checkObjectOperation(Scope scope) throws RuntimeException {
		Value[] operands = getOperands(scope);
		if (operands.length == 0) {
			return false;
		}
		if (operands[0] instanceof ObjectValue) {
			ObjectValue object = (ObjectValue) operands[0];
			// TODO: Call operator function
			return true;
		}
		return false;
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
	public abstract Value[] getOperands(Scope scope) throws RuntimeException;
}
