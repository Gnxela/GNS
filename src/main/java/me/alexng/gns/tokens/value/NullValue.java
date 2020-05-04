package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;

public class NullValue extends Value {

	public static final NullValue INTERNAL = new NullValue(FileIndex.INTERNAL_INDEX);

	public NullValue(FileIndex fileIndex) {
		super(Type.NULL, fileIndex);
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
