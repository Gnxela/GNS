package me.alexng.gns;

import me.alexng.gns.lexer.Token;

public class RuntimeException extends GNSException {

	public RuntimeException(int startIndex, int endIndex, String message) {
		super(startIndex, endIndex, message);
	}

	public RuntimeException(Token token, String message) {
		super(token, message);
	}

	public RuntimeException(int index, String message) {
		super(index, message);
	}
}
