package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.*;

import java.util.ListIterator;

// TODO: Better name
public class ObjectConstructionConstructor implements Constructor {

	// TODO: This share a lot of code with {@link FunctionCallConstructor}. Remove duplication?

	@Override
	public boolean accepts(Token token) {
		return token instanceof KeywordToken && ((KeywordToken) token).getKeyword() == Keyword.NEW;
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keywordToken = (KeywordToken) tokens.next();
		tokens.remove();
		IdentifierToken classIdentifier = (IdentifierToken) tokens.next();
		tokens.remove();
		ArgumentsToken arguments = ArgumentsConstructor.construct(tokens);
		tokens.add(new ObjectConstructionToken(classIdentifier, arguments, FileIndex.openClose(keywordToken, arguments)));
	}
}
