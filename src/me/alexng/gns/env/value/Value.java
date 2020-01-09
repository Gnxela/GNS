package me.alexng.gns.env.value;

public abstract class Value {

	public static final Value NULL = new Value(Type.NULL) {
		@Override
		public Object getJavaValue() {
			return null;
		}

		@Override
		public String toString() {
			return "<NullValue >";
		}
	};
	private Type type;

	public Value(Type type) {
		this.type = type;
	}

	public abstract Object getJavaValue();

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
