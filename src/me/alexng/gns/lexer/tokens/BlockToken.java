package me.alexng.gns.lexer.tokens;

import me.alexng.gns.lexer.Token;

import java.util.List;

public class BlockToken extends Token{

	private List<Token> tokens;

	public BlockToken(List<Token> tokens) {
		// TODO: We don't want tokens here. Temp measure.
		this.tokens = tokens;
	}

	@Override
	public String toString() {
		// TODO: Include block contents
		return "<Block " + tokens.toString() + ">";
	}
}
