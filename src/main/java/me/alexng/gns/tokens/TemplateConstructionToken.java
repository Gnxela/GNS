package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.Value;

public class TemplateConstructionToken extends IdentifiedToken {

	private ArgumentsToken argumentsToken;

	public TemplateConstructionToken(IdentifierToken identifier, ArgumentsToken argumentsToken, FileIndex fileIndex) {
		super(identifier, fileIndex);
		this.argumentsToken = argumentsToken;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value[] values = argumentsToken.grabValues(scope);
		TemplateToken templateToken = scope.getTemplate(getIdentifier());
		return templateToken.createInstance(this, values, scope);
	}

	@Override
	public String toString() {
		return "<ObjectConstruction " + getIdentifier().getName() + " " + argumentsToken.toString() + ">";
	}
}
