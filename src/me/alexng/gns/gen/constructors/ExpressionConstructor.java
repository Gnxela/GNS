package me.alexng.gns.gen.constructors;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.BracketToken;
import me.alexng.gns.lexer.tokens.ExpressionToken;

import java.util.LinkedList;
import java.util.ListIterator;

public class ExpressionConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return BracketToken.ROUND_OPEN.matches(token);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		BracketToken openBracket = (BracketToken) tokens.next();
		tokens.remove();

		LinkedList<Token> expressionTokens = Assembler.matchTokens(tokens, BracketToken.ROUND_OPEN, BracketToken.ROUND_CLOSED);
		Assembler.assemble(expressionTokens);
		if (expressionTokens.size() == 0) {
			throw new ParsingException(openBracket, "Empty brackets");
		}
		if (expressionTokens.size() != 1) {
			throw new ParsingException(expressionTokens.get(1), "Invalid syntax");
		}
		// TODO: Set end index
		tokens.add(new ExpressionToken(expressionTokens.getFirst(), openBracket.getStartIndex(), 0));
	}
}
