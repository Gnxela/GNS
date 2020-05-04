package me.alexng.gns;

import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.value.Value;

public class InvalidTypeException extends RuntimeException {

	public InvalidTypeException(FileIndex fileIndex, Token expected, Token got) {
		super(fileIndex, "Invalid type. Expected: " + expected.toString() + ". Received: " + got.toString());
	}

	public InvalidTypeException(Token token, Token expected, Token got) {
		super(token, "Invalid type. Expected: " + expected.toString() + ". Received: " + got.toString());
	}

	public InvalidTypeException(Token token, Class<? extends Token> expected, Token got) {
		super(token, "Invalid type. Expected: " + expected.getSimpleName() + ". Received: " + got.toString());
	}

	public InvalidTypeException(Token token, Value.Type expected, Value.Type got) {
		super(token, "Invalid type. Expected: " + expected.toString() + ". Received: " + got.toString());
	}
}
