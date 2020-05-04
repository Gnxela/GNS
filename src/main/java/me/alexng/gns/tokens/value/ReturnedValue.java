package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;

public class ReturnedValue extends Value {

	private Value returnedValue;

	public ReturnedValue(Value returnedValue) {
		super(Type.NULL, FileIndex.INTERNAL_INDEX);
		this.returnedValue = returnedValue;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ReturnedValue) {
			return returnedValue.equals(((ReturnedValue) o).returnedValue);
		}
		return false;
	}

	@Override
	public Value getJavaValue() {
		return returnedValue;
	}
}
