package me.alexng.gns;

import me.alexng.gns.lexer.Token;

public class RuntimeException extends Exception {

	private int startIndex, endIndex;

	public RuntimeException(int startIndex, int endIndex, String message) {
		super("Runtime error at index " + startIndex + ". Message: " + message);
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public RuntimeException(Token token, String message) {
		this(token.getStartIndex(), token.getEndIndex(), message);
	}

	public RuntimeException(int index, String message) {
		this(index, index + 1, message);
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

}
