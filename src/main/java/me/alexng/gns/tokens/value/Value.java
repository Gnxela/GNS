package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.tokens.ValueToken;

public abstract class Value {

	public static final NullValue NULL = new NullValue();
	private Type type;

	public Value(Type type) {
		this.type = type;
	}

	public ValueToken wrap() {
		return new ValueToken(this, FileIndex.INTERNAL_INDEX);
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
