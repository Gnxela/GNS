package me.alexng.gns.gen.constructors;

import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.BlockToken;
import me.alexng.gns.lexer.tokens.BracketToken;
import me.alexng.gns.lexer.tokens.IfToken;
import me.alexng.gns.lexer.tokens.KeywordToken;

import java.util.LinkedList;
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

		if (!tokens.next().matches(BracketToken.ROUND_OPEN)) {
			// TODO: Better exception
			throw new ParsingException(0, "Expected open bracket");
		}
		tokens.remove();

		LinkedList<Token> conditions = Assembler.matchTokens(tokens, BracketToken.ROUND_OPEN, BracketToken.ROUND_CLOSED);
		Assembler.assemble(conditions);
		if (conditions.size() != 1) {
			if (conditions.size() == 0) {
				throw new ParsingException(keyword.getStartIndex(), "Missing condition");
			}
			throw new ParsingException(conditions.get(1).getStartIndex(), "Invalid syntax");
		}
		Token condition = conditions.getFirst();

		Token expectedBlock = tokens.next();
		if (!(expectedBlock instanceof BlockToken)) {
			// TODO: Better exception
			throw new ParsingException(0, "Expected block");
		}
		tokens.remove();

		BlockToken block = (BlockToken) expectedBlock;
		// TODO: Fill in start and end index
		tokens.add(new IfToken(condition, block, keyword.getStartIndex(), 0));

	}
}
