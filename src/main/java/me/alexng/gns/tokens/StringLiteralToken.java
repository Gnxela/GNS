package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.value.StringValue;
import me.alexng.gns.env.value.Value;

public class StringLiteralToken extends Token {

	private String value;

	public StringLiteralToken(String value, FileIndex fileIndex) {
		super(fileIndex);
		this.value = value;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return StringValue.createString(value, scope);
	}

	@Override
	public String toString() {
		return "<StringLiteral " + value + ">";
	}
}
