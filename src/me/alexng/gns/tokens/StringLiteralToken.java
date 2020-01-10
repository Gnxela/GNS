package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

public class StringLiteralToken extends Token {

	private String value;

	public StringLiteralToken(String value, FileIndex fileIndex) {
		super(fileIndex);
		this.value = value;
	}

	@Override
	public String toString() {
		return "<StringLiteral " + value + ">";
	}
}
