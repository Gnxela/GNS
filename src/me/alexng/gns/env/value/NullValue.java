package me.alexng.gns.env.value;

public class NullValue extends Value {

	private final ValueDescriptor<NullValue> NULL_DESCRIPTOR = new ValueDescriptor<NullValue>() {
		@Override
		public String getTypeString() {
			return "null";
		}

		@Override
		public NullValue castTo(Value value) throws RuntimeException {
			return Value.NULL;
		}
	};

	NullValue() {
		super(Type.NULL);
	}

	@Override
	public Object getJavaValue() {
		return null;
	}

	@Override
	public ValueDescriptor<NullValue> getValueDescriptor() {
		return NULL_DESCRIPTOR;
	}

	@Override
	public String toString() {
		return "<NullValue >";
	}
}
