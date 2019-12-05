package me.alexng.gns.gen.constructors;

import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.*;

import java.util.ListIterator;

public class FunctionConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return token instanceof KeywordToken && ((KeywordToken) token).getKeyword() == Keyword.FUNC;
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keyword = (KeywordToken) tokens.next();
		tokens.remove();

		IdentifierToken identifier = (IdentifierToken) tokens.next();
		tokens.remove();

		Token expectedParameters = tokens.next();
		// TODO: Create parameter token
		if (!(expectedParameters instanceof ExpressionToken)) {
			// TODO: Create generic function for this.
			throw new ParsingException(0, "Expected block. Found " + expectedParameters.getClass().getSimpleName());
		}
		tokens.remove();


		Token expectedBlock = tokens.next();
		if (!(expectedBlock instanceof BlockToken)) {
			// TODO: Create generic function for this.
			throw new ParsingException(0, "Expected block. Found " + expectedBlock.getClass().getSimpleName());
		}
		tokens.remove();
		BlockToken block = (BlockToken) expectedBlock;

		tokens.add(new FunctionToken(identifier, block, keyword.getStartIndex(), block.getEndIndex()));
	}
}
