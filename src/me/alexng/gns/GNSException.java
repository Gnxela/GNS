package me.alexng.gns;

public class GNSException extends Exception {

	private FileIndex fileIndex;

	public GNSException(FileIndex fileIndex, String message) {
		super(message);
		this.fileIndex = fileIndex;
	}

	public void printErrorSource(String source) {
		System.err.println("Error at: " + fileIndex.substring(source));
	}

	public int getStartIndex() {
		return fileIndex.getStartIndex();
	}

	public int getEndIndex() {
		return fileIndex.getEndIndex();
	}
}
