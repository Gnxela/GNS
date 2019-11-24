package me.alexng.gns.lexer.tokens;

import me.alexng.gns.lexer.Token;

public class FunctionToken extends Token {

	private String name;

	public FunctionToken(String name, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.name = name;
	}

	@Override
	public String toString() {
		return "<Function " + name + ">";
	}
}
