package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.ReturnedValue;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;

public class FunctionCallToken extends IdentifiedToken {

	private ArgumentsToken argumentsToken;

	public FunctionCallToken(IdentifierToken identifier, ArgumentsToken argumentsToken, FileIndex fileIndex) {
		super(identifier, fileIndex);
		this.argumentsToken = argumentsToken;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value[] values = argumentsToken.grabValues(scope);
		Scope callingScope = scope.getObjectScope() != null ? scope.getObjectScope() : scope.getGlobalScope();
		Value returnedValue = scope.getFunction(this).executeFunction(this, callingScope, values);
		if (returnedValue instanceof ReturnedValue) {
			return ((ReturnedValue) returnedValue).getJavaValue();
		}
		return Value.NULL;
	}

	@Override
	public String toString() {
		return "<FunctionCall " + getIdentifier().getName() + " " + argumentsToken.toString() + ">";
	}
}
