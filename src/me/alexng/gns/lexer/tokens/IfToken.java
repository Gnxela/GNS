package me.alexng.gns.lexer.tokens;

import me.alexng.gns.lexer.Token;

import java.util.List;

public class IfToken extends Token {

	private List<Token> condition;
	private BlockToken block;

	public IfToken(List<Token> condition, BlockToken block, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.condition = condition;
		this.block = block;
	}

	@Override
	public String toString() {
		return "<If " + condition.toString() + ", " + block.toString() + ">";
	}
}