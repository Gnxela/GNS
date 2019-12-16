package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.GNSException;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.BracketToken;
import me.alexng.gns.tokens.Token;

public class BracketGenerator implements TokenGenerator {

	private static final Character[] BRACKETS = new Character[]{'(', ')', '{', '}', '[', ']'};

	@Override
	public int accepts(String input, int index) {
		char firstCharacter = input.charAt(index);
		for (Character bracket : BRACKETS) {
			if (firstCharacter == bracket) {
				return index + 1;
			}
		}
		return index;
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) throws GNSException {
		switch (input.charAt(fileIndex.getStartIndex())) {
			case '(':
				return new BracketToken(BracketToken.Bracket.ROUND, BracketToken.Type.OPEN, fileIndex);
			case ')':
				return new BracketToken(BracketToken.Bracket.ROUND, BracketToken.Type.CLOSED, fileIndex);
			case '[':
				return new BracketToken(BracketToken.Bracket.SQUARE, BracketToken.Type.OPEN, fileIndex);
			case ']':
				return new BracketToken(BracketToken.Bracket.SQUARE, BracketToken.Type.CLOSED, fileIndex);
			case '{':
				return new BracketToken(BracketToken.Bracket.CURLY, BracketToken.Type.OPEN, fileIndex);
			case '}':
				return new BracketToken(BracketToken.Bracket.CURLY, BracketToken.Type.CLOSED, fileIndex);
			default:
				throw new GNSException(fileIndex, "Bracket generation failed");
		}
	}
}