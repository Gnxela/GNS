package me.alexng.gns.env.value;

public class NumberValue extends Value {

	private final ValueDescriptor<NumberValue> DESCRIPTOR = new ValueDescriptor<NumberValue>() {
		@Override
		public String getTypeString() {
			return "number";
		}

		@Override
		public NumberValue castTo(Value value) throws RuntimeException {
			return (NumberValue) value;
		}
	};

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

	@Override
	public ValueDescriptor<NumberValue> getValueDescriptor() {
		return DESCRIPTOR;
	}

	public boolean isInt() {
		return isInt;
	}
}
