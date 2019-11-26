package me.alexng.gns;

public class ParsingException extends Exception {

	// TODO: Allow for a range of index's.
	public ParsingException(int index, String message) {
		super("Parsing error at index " + index + ". Message: " + message);
	}
}
