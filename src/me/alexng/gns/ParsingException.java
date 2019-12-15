package me.alexng.gns;

import me.alexng.gns.tokens.Token;

public class ParsingException extends GNSException {

	public ParsingException(int startIndex, int endIndex, String message) {
		super(startIndex, endIndex, "Parsing error at index " + startIndex + ". Message: " + message);
	}

	public ParsingException(Token token, String message) {
		this(token.getStartIndex(), token.getEndIndex(), message);
	}

	public ParsingException(int index, String message) {
		this(index, index + 1, message);
	}
}
