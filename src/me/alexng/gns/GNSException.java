package me.alexng.gns;

import me.alexng.gns.tokens.Token;

public class GNSException extends Exception {

	private int startIndex, endIndex;

	public GNSException(int startIndex, int endIndex, String message) {
		super("Parsing error at index " + startIndex + ". Message: " + message);
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public GNSException(Token token, String message) {
		this(token.getStartIndex(), token.getEndIndex(), message);
	}

	public GNSException(int index, String message) {
		this(index, index + 1, message);
	}

	public void printErrorSource(String source) {
		System.err.println("Error at: " + source.substring(startIndex, endIndex));
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

}
