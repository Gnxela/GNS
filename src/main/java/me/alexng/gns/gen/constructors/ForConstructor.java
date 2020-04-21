package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.*;

import java.util.ListIterator;

public class ForConstructor implements Constructor {

	@Override
	public boolean isLeftToRight() {
		return true;
	}

	@Override
	public boolean accepts(Token token) {
		return Keyword.FOR.matches(token);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keywordToken = Assembler.castTo(KeywordToken.class, tokens.next());
		tokens.remove();
		ArgumentsToken argumentsToken = ArgumentsConstructor.construct(tokens);
		if (argumentsToken.getArguments().length != 3) {
			throw new ParsingException(argumentsToken.getFileIndex(), "Expected 3 expressions. Found " + argumentsToken.getArguments().length);
		}
		BlockToken blockToken = Assembler.castTo(BlockToken.class, tokens.next());
		tokens.remove();
		tokens.add(new ForToken(argumentsToken, blockToken, FileIndex.openClose(keywordToken, blockToken)));
	}
}
