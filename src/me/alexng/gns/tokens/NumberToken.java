package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.env.NumberValue;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;

public class NumberToken extends Token {

	private NumberValue value;

	public NumberToken(String value, FileIndex fileIndex) {
		super(fileIndex);
		this.value = new NumberValue(value);
	}

	@Override
	public Value execute(Scope scope) {
		return value;
	}

	@Override
	public String toString() {
		return "<Number " + value + ">";
	}
}