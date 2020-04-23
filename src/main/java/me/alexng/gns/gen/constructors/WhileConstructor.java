package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.*;

import java.util.ListIterator;

public class WhileConstructor implements Constructor {

	@Override
	public boolean isLeftToRight() {
		return true;
	}

	@Override
	public boolean accepts(Token token) {
		return Keyword.WHILE.matches(token);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keywordToken = Assembler.castTo(KeywordToken.class, tokens.next());
		tokens.remove();
		ExpressionToken expressionToken = ExpressionConstructor.constructExpression(tokens);
		BlockToken blockToken = Assembler.castTo(BlockToken.class, tokens.next());
		tokens.remove();
		tokens.add(new WhileToken(expressionToken, blockToken, FileIndex.openClose(keywordToken, blockToken)));
	}
}
