package me.alexng.gns.env;

public abstract class Value {

	public static final Value NULL = new Value(Type.NULL) {
		@Override
		public Object getValue() {
			return null;
		}
	};
	private Type type;

	public Value(Type type) {
		this.type = type;
	}

	public abstract Object getValue();

	public Type getType() {
		return type;
	}

	public enum Type {
		NULL,
		BOOLEAN,
		STRING,
		NUMBER,
		OBJECT;
	}
}
