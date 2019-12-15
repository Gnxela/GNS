package me.alexng.gns;

import me.alexng.gns.tokens.Token;

public class RuntimeException extends GNSException {

	public RuntimeException(int startIndex, int endIndex, String message) {
		super(startIndex, endIndex, "Runtime error at index " + startIndex + ". Message: " + message);
	}

	public RuntimeException(Token token, String message) {
		this(token.getStartIndex(), token.getEndIndex(), message);
	}

	public RuntimeException(int index, String message) {
		this(index, index + 1, message);
	}
}
