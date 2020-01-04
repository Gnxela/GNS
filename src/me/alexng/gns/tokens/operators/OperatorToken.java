package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.tokens.IdentifiedToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.OperatorFunctionToken;

public abstract class OperatorToken extends IdentifiedToken {

	private boolean bound = false;

	public OperatorToken(String operatorString, FileIndex fileIndex) {
		super(new IdentifierToken(operatorString, fileIndex), fileIndex);
	}

	/**
	 * @return a value if the operation was forwarded to an object. Otherwise null.
	 */
	public Value tryObjectOperation(Scope scope) throws RuntimeException {
		Value[] operands = getOperands(scope);
		if (operands.length == 0) {
			return null;
		}
		if (!(operands[0] instanceof ObjectValue)) {
			return null;
		}
		ObjectValue object = (ObjectValue) operands[0];
		OperatorFunctionToken operatorFunction = object.getObjectScope().operatorFunctionProvider.get(getIdentifier());
		if (operatorFunction == null) {
			throw new RuntimeException(this, "Operation not defined for object");
		}
		Value[] values = new Value[operands.length - 1];
		System.arraycopy(operands, 1, values, 0, values.length);
		return operatorFunction.unwrapAndExecuteFunction(this, object.getObjectScope(), values);
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
