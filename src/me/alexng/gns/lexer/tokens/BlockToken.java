package me.alexng.gns.lexer.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
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

	/**
	 * Executes the block.
	 *
	 * @return The local scope of the block after it has finished executing.
	 */
	public Scope executeBlock(Scope parentScope) throws RuntimeException {
		Scope localScope = new Scope(parentScope);
		for (Token token : tokens) {
			if (token instanceof EOLToken) {
				continue;
			}
			token.execute(localScope);
		}
		return localScope;
	}

	@Override
	public String toString() {
		return "<Block \n" + StringUtil.indent(StringUtil.unrollList(tokens)) + ">";
	}
}
