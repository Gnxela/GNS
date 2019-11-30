package me.alexng.gns;

import me.alexng.gns.lexer.Token;

public class ParsingException extends GNSException {

	public ParsingException(int startIndex, int endIndex, String message) {
		super(startIndex, endIndex, message);
	}

	public ParsingException(Token token, String message) {
		super(token, message);
	}

	public ParsingException(int index, String message) {
		super(index, message);
	}

}
