package me.alexng.gns.gen.constructors;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.BlockToken;
import me.alexng.gns.lexer.tokens.BracketToken;

import java.util.LinkedList;
import java.util.ListIterator;

public class BlockConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return BracketToken.CURLEY_OPEN.equals(token);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		BracketToken openBracket = (BracketToken) tokens.next();
		tokens.remove();

		LinkedList<Token> blockTokens = Assembler.matchTokens(tokens, BracketToken.CURLEY_OPEN, BracketToken.CURLEY_CLOSED);
		// TODO: We need to run the assembler on these newly created blocks as well.
		// TODO: Fill in start and end index
		tokens.add(new BlockToken(blockTokens, openBracket.getStartIndex(), 0));
	}
}
