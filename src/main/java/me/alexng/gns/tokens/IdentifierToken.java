package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.Value;

public class IdentifierToken extends IdentifiedToken {

	private String name;

	public IdentifierToken(String name, FileIndex fileIndex) {
		super(null, fileIndex);
		this.name = name;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return scope.getValue(this);
	}

	@Override
	public IdentifierToken getIdentifier() {
		return this;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "<Identifier " + name + ">";
	}
}