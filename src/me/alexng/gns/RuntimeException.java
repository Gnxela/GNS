package me.alexng.gns;

public class RuntimeException extends Exception {

	// TODO: Allow for a range of index's.
	public RuntimeException(int index, String message) {
		super("Runtime error at index " + index + ". Message: " + message);
	}

}
