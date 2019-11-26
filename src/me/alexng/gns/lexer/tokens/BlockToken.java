package me.alexng.gns.lexer.tokens;

import me.alexng.gns.lexer.Token;
import me.alexng.gns.util.StringUtil;

import java.util.List;

public class BlockToken extends Token {

	private List<Token> tokens;

	public BlockToken(List<Token> tokens, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		// TODO: We don't want tokens here. Temp measure.
		this.tokens = tokens;
	}

	@Override
	public String toString() {
		return "<Block \n" + StringUtil.indent(StringUtil.unrollList(tokens)) + ">";
	}
}
