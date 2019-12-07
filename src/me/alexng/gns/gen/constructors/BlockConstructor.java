package me.alexng.gns.gen.constructors;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.tokens.BlockToken;
import me.alexng.gns.tokens.BracketToken;

import java.util.LinkedList;
import java.util.ListIterator;

public class BlockConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return BracketToken.CURLEY_OPEN.matches(token);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		LinkedList<Token> blockTokens = Assembler.matchTokens(tokens, BracketToken.CURLEY_OPEN, BracketToken.CURLEY_CLOSED);
		BracketToken openBracket = (BracketToken) blockTokens.removeFirst();
		BracketToken closeBracket = (BracketToken) blockTokens.removeLast();

		Assembler.assemble(blockTokens);
		tokens.add(new BlockToken(blockTokens, openBracket.getStartIndex(), closeBracket.getEndIndex()));
	}
}
