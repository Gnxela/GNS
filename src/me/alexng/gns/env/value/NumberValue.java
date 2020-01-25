package me.alexng.gns.env.value;

public class NumberValue extends Value {

	private boolean isInt;
	private int intValue;
	private double doubleValue;

	public NumberValue(String value) {
		super(Type.NUMBER);
		parseValue(value);
	}

	public NumberValue(int value) {
		super(Type.NUMBER);
		this.isInt = true;
		this.intValue = value;
	}

	public NumberValue(double value) {
		super(Type.NUMBER);
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
