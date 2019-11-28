package me.alexng.gns;

public class ParsingException extends Exception {

	private int startIndex, endIndex;

	public ParsingException(int startIndex, int endIndex, String message) {
		super("Parsing error at index " + startIndex + ". Message: " + message);
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public ParsingException(int index, String message) {
		this(index, index + 1, message);
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}
}
