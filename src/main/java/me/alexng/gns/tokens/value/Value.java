package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.Token;

public abstract class Value extends Token {

	private Type type;

	public Value(Type type, FileIndex fileIndex) {
		super(fileIndex);
		this.type = type;
	}

	public abstract Object getJavaValue();

	/**
	 * Returns true if the values are of the same type and have the same value. NOT if they are the same instance.
	 */
	@Override
	public abstract boolean equals(Object o);

	@Override
	public int hashCode() {
		return type != null ? type.hashCode() : 0;
	}

	@Override
	public Value execute(Scope scope) {
		return this;
	}

	@Override
	public String toString() {
		return "<" + getClass().getSimpleName() + " " + getJavaValue().toString() + " >";
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
