package me.alexng.gns.env;

public class BooleanValue extends Value {

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
	public String toString() {
		return "<BooleanValue " + getValue() + " >";
	}

	@Override
	public Boolean getValue() {
		return value;
	}
}
