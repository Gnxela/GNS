package me.alexng.gns.env;

public class ReturnedValue extends Value {

	private Value returnedValue;

	public ReturnedValue(Value returnedValue) {
		super(Type.NULL);
		this.returnedValue = returnedValue;
	}

	@Override
	public Value getJavaValue() {
		return returnedValue;
	}
}
