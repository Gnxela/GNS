package me.alexng.gns.gen.constructors;

import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.KeywordToken;
import me.alexng.gns.tokens.Token;

import java.util.ListIterator;

public class KeywordConstructor implements Constructor {

	@Override
	public boolean isLeftToRight() {
		return true;
	}

	@Override
	public boolean accepts(Token token) {
		return token instanceof IdentifierToken;
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		IdentifierToken identifierToken = (IdentifierToken) tokens.next();
		for (Keyword keyword : Keyword.values()) {
			if (identifierToken.getName().equals(keyword.getKeyword())) {
				tokens.remove();
				tokens.add(new KeywordToken(keyword, identifierToken.getFileIndex()));
				return;
			}
		}
	}
}
