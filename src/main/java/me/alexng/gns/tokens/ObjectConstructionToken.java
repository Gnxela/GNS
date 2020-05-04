package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.value.Value;

public class ObjectConstructionToken extends IdentifiedToken {

	private ArgumentsToken argumentsToken;

	public ObjectConstructionToken(IdentifierToken identifier, ArgumentsToken argumentsToken, FileIndex fileIndex) {
		super(identifier, fileIndex);
		this.argumentsToken = argumentsToken;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value[] values = argumentsToken.grabValues(scope);
		ClassToken classToken = scope.getTemplate(getIdentifier());
		return classToken.createInstance(this, values, scope);
	}

	@Override
	public String toString() {
		return "<ObjectConstruction " + getIdentifier().getName() + " " + argumentsToken.toString() + ">";
	}
}
