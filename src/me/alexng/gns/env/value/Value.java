package me.alexng.gns.env.value;

public abstract class Value {

	public static final NullValue NULL = new NullValue();
	private Type type;

	public Value(Type type) {
		this.type = type;
	}

	public abstract Object getJavaValue();

	public abstract ValueDescriptor getValueDescriptor();

	public String toString() {
		return getJavaValue().toString();
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		NULL,
		BOOLEAN,
		NUMBER,
		OBJECT;
	}
}
