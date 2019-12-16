package me.alexng.gns;

public class GNSException extends Exception {

	private int startIndex, endIndex;

	public GNSException(int startIndex, int endIndex, String message) {
		super(message);
		this.startIndex = startIndex;
		this.endIndex = endIndex;
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
