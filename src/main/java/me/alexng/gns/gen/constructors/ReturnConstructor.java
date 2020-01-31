package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.KeywordToken;
import me.alexng.gns.tokens.ReturnToken;
import me.alexng.gns.tokens.Token;

import java.util.ListIterator;

public class ReturnConstructor implements Constructor {

	@Override
	public boolean isLeftToRight() {
		return true;
	}

	@Override
	public boolean accepts(Token token) {
		return Keyword.RETURN.matches(token);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keywordToken = Assembler.castTo(KeywordToken.class, tokens.next());
		tokens.remove();
		Token returnedToken = tokens.next();
		tokens.remove();
		tokens.add(new ReturnToken(returnedToken, FileIndex.openClose(keywordToken, returnedToken)));
	}
}
