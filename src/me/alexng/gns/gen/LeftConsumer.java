package me.alexng.gns.gen;

import me.alexng.gns.lexer.Token;
import me.alexng.gns.util.ArrayUtil;

import java.util.*;

public class LeftConsumer implements Consumer {

	private Class<? extends Token>[] allowedTokens;

	public LeftConsumer(Class<? extends Token>[] allowedTokens) {
		this.allowedTokens = allowedTokens;
	}

	@Override
	public List<Token> consume(ListIterator<Token> tokens) {
		if (tokens.hasPrevious()) {
			Token token = tokens.previous();
			if (ArrayUtil.arrayContains(allowedTokens, token.getClass())) {
				tokens.remove();
				tokens.next();
				return Collections.singletonList(token);
			}
		}
		return Collections.emptyList();
	}
}
