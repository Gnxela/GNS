package me.alexng.gns.lexer.tokens;

import me.alexng.gns.GNSException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.util.StringUtil;

import java.util.Iterator;
import java.util.List;

public class BlockToken extends Token {

	private List<Token> tokens;

	public BlockToken(List<Token> tokens, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		// TODO: We don't want tokens here. Temp measure.
		this.tokens = tokens;
	}

	@Override
	public Value execute(Scope scope) throws GNSException {
		Iterator<Token> iterator = tokens.iterator();
		while (iterator.hasNext()) {
			Token token = iterator.next();
			if (token instanceof EOLToken) {
				continue;
			}
			token.execute(scope);
		}
		return Value.NULL;
	}

	@Override
	public String toString() {
		return "<Block \n" + StringUtil.indent(StringUtil.unrollList(tokens)) + ">";
	}
}
