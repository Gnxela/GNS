package me.alexng.gns.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;

public class IdentifierToken extends Token {

	private String name;

	public IdentifierToken(String name, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.name = name;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return scope.getVariable(this);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "<Identifier " + name + ">";
	}
}