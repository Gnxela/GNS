package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.*;

import java.util.ListIterator;

public class ObjectConstructionConstructor implements Constructor {

	@Override
	public boolean isLeftToRight() {
		return true;
	}

	@Override
	public boolean accepts(Token token) {
		return Keyword.NEW.matches(token);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keywordToken = Assembler.castTo(KeywordToken.class, tokens.next());
		tokens.remove();
		IdentifierToken classIdentifier = Assembler.castTo(IdentifierToken.class, tokens.next());
		tokens.remove();
		ArgumentsToken arguments = ArgumentsConstructor.construct(tokens);
		tokens.add(new ObjectConstructionToken(classIdentifier, arguments, FileIndex.openClose(keywordToken, arguments)));
	}
}
