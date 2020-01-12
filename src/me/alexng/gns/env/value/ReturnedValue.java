package me.alexng.gns.env.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;

public class ReturnedValue extends Value {

	private final ValueDescriptor<ReturnedValue> DESCRIPTOR = new ValueDescriptor<ReturnedValue>() {
		@Override
		public String getTypeString() {
			return "returnedValue";
		}

		@Override
		public ReturnedValue castTo(Value value) throws RuntimeException {
			throw new RuntimeException(FileIndex.NULL_INDEX, "Cast to ReturnedValue");
		}
	};

	private Value returnedValue;

	public ReturnedValue(Value returnedValue) {
		super(Type.NULL);
		this.returnedValue = returnedValue;
	}

	@Override
	public Value getJavaValue() {
		return returnedValue;
	}

	@Override
	public ValueDescriptor<ReturnedValue> getValueDescriptor() {
		return DESCRIPTOR;
	}
}
