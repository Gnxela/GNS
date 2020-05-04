package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;

public class NumberValue extends Value {

	private boolean isInt;
	private int intValue;
	private double doubleValue;

	public NumberValue(String value, FileIndex fileIndex) {
		super(Type.NUMBER, fileIndex);
		parseValue(value);
	}

	public NumberValue(int value, FileIndex fileIndex) {
		super(Type.NUMBER, fileIndex);
		this.isInt = true;
		this.intValue = value;
	}

	public NumberValue(double value, FileIndex fileIndex) {
		super(Type.NUMBER, fileIndex);
		this.isInt = false;
		this.doubleValue = value;
	}

	private void parseValue(String value) {
		if (value.contains(".")) {
			doubleValue = Double.parseDouble(value);
			isInt = false;
		} else {
			intValue = Integer.parseInt(value);
			isInt = true;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof NumberValue) {
			return getJavaValue().equals(((NumberValue) o).getJavaValue());
		}
		return false;
	}

	@Override
	public Number getJavaValue() {
		if (isInt) {
			return intValue;
		}
		return doubleValue;
	}

	public boolean isInt() {
		return isInt;
	}
}
