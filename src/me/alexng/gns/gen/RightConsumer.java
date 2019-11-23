package me.alexng.gns.gen;

import me.alexng.gns.lexer.Token;
import me.alexng.gns.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class RightConsumer implements Consumer {

	private Class<? extends Token>[] allowedTokens;

	public RightConsumer(Class<? extends Token>[] allowedTokens) {
		this.allowedTokens = allowedTokens;
	}

	@Override
	public List<Token> consume(ListIterator<Token> tokens) {
		if (tokens.hasNext()) {
			Token token = tokens.next();
			if (ArrayUtil.arrayContains(allowedTokens, token.getClass())) {
				tokens.remove();
				tokens.previous();
				return Collections.singletonList(token);
			}
		}
		return Collections.emptyList();
	}
}
