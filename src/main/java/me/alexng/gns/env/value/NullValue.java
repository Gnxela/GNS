package me.alexng.gns.env.value;

public class NullValue extends Value {

	NullValue() {
		super(Type.NULL);
	}

	@Override
	public Object getJavaValue() {
		return null;
	}

	@Override
	public String toString() {
		return "<NullValue >";
	}
}
