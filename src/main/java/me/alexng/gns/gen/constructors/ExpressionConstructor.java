package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.BracketToken;
import me.alexng.gns.tokens.ExpressionToken;
import me.alexng.gns.tokens.Token;

import java.util.LinkedList;
import java.util.ListIterator;

public class ExpressionConstructor implements Constructor {

	@Override
	public boolean isLeftToRight() {
		return true;
	}

	@Override
	public boolean accepts(Token token) {
		return BracketToken.ROUND_OPEN.matches(token);
	}

	public static ExpressionToken constructExpression(ListIterator<Token> tokens) throws ParsingException {
		LinkedList<Token> expressionTokens = Assembler.matchTokens(tokens, BracketToken.ROUND_OPEN, BracketToken.ROUND_CLOSED);
		FileIndex fileIndex = FileIndex.wrap(expressionTokens);
		expressionTokens.removeFirst();
		expressionTokens.removeLast();

		Assembler.assemble(expressionTokens);
		if (expressionTokens.size() == 0) {
			throw new ParsingException(fileIndex, "Empty brackets");
		}
		if (expressionTokens.size() != 1) {
			throw new ParsingException(fileIndex, "Invalid syntax");
		}
		return new ExpressionToken(expressionTokens.getFirst(), fileIndex);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		tokens.add(constructExpression(tokens));
	}

}
