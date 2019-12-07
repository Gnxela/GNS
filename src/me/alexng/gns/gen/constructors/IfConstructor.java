package me.alexng.gns.gen.constructors;

import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.tokens.BlockToken;
import me.alexng.gns.tokens.ExpressionToken;
import me.alexng.gns.tokens.IfToken;
import me.alexng.gns.tokens.KeywordToken;
import me.alexng.gns.util.ExceptionUtil;

import java.util.ListIterator;

public class IfConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return token instanceof KeywordToken && ((KeywordToken) token).getKeyword() == Keyword.IF;
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keyword = (KeywordToken) tokens.next();
		tokens.remove();

		ExpressionToken expression = (ExpressionToken) tokens.next();
		tokens.remove();

		Token expectedBlock = tokens.next();
		if (!(expectedBlock instanceof BlockToken)) {
			throw ExceptionUtil.createParsingExpected("Invalid syntax", BlockToken.class, expectedBlock);
		}
		tokens.remove();

		BlockToken block = (BlockToken) expectedBlock;
		tokens.add(new IfToken(expression, block, keyword.getStartIndex(), block.getEndIndex()));
	}
}
