package me.alexng.gns.env;

public class NumberValue extends Value {

	private boolean isInt;
	private int intValue;
	private double doubleValue;

	public NumberValue(String value) {
		super(Type.NUMBER);
		parseValue(value);
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
	public Number getValue() {
		if (isInt) {
			return intValue;
		}
		return doubleValue;
	}

	@Override
	public String toString() {
		return "<NumberValue " + getValue() + " >";
	}

	public boolean isInt() {
		return isInt;
	}
}
