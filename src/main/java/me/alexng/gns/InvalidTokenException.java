package me.alexng.gns;

import me.alexng.gns.tokens.Token;

public class InvalidTokenException extends ParsingException {

	public InvalidTokenException(FileIndex fileIndex, Token expected, Token got) {
		super(fileIndex, "Invalid token. Expected: " + expected.toString() + ". Received: " + got.toString());
	}

	public InvalidTokenException(Token token, Token expected, Token got) {
		super(token, "Invalid token. Expected: " + expected.toString() + ". Received: " + got.toString());
	}

	public InvalidTokenException(Token token, Class<? extends Token> expected, Token got) {
		super(token, "Invalid token. Expected: " + expected.getSimpleName() + ". Received: " + got.toString());
	}
}
