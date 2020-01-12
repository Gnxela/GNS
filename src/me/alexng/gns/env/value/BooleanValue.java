package me.alexng.gns.env.value;

public class BooleanValue extends Value {

	private final ValueDescriptor<BooleanValue> DESCRIPTOR = new ValueDescriptor<BooleanValue>() {
		@Override
		public String getTypeString() {
			return "boolean";
		}

		@Override
		public BooleanValue castTo(Value value) throws RuntimeException {
			return (BooleanValue) value;
		}
	};

	public static final BooleanValue TRUE = new BooleanValue(true);
	public static final BooleanValue FALSE = new BooleanValue(false);

	private boolean value;

	private BooleanValue(boolean value) {
		super(Type.BOOLEAN);
		this.value = value;
	}

	public static BooleanValue valueOf(boolean bool) {
		return bool ? TRUE : FALSE;
	}

	@Override
	public Boolean getJavaValue() {
		return value;
	}

	@Override
	public ValueDescriptor<BooleanValue> getValueDescriptor() {
		return DESCRIPTOR;
	}
}
