package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.*;
import me.alexng.gns.util.ExceptionUtil;

import java.util.ListIterator;

public class ClassConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return token instanceof KeywordToken && ((KeywordToken) token).getKeyword() == Keyword.CLASS;
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keyword = (KeywordToken) tokens.next();
		tokens.remove();

		IdentifierToken identifier = (IdentifierToken) tokens.next();
		tokens.remove();

		Token expectedBlock = tokens.next();
		if (!(expectedBlock instanceof BlockToken)) {
			throw ExceptionUtil.createParsingExpected("Invalid syntax", BlockToken.class, expectedBlock);
		}
		tokens.remove();
		BlockToken block = (BlockToken) expectedBlock;

		tokens.add(new ClassToken(identifier, block, FileIndex.openClose(keyword, block)));
	}
}