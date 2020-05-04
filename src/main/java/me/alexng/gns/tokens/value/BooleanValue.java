package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;

public class BooleanValue extends Value {

	public static final BooleanValue INTERNAL_TRUE = new BooleanValue(true, FileIndex.INTERNAL_INDEX);
	public static final BooleanValue INTERNAL_FALSE = new BooleanValue(false, FileIndex.INTERNAL_INDEX);

	private boolean value;

	public BooleanValue(boolean value, FileIndex fileIndex) {
		super(Type.BOOLEAN, fileIndex);
		this.value = value;
	}

	public static BooleanValue valueOf(boolean bool) {
		return bool ? INTERNAL_TRUE : INTERNAL_FALSE;
	}

	@Override
	public Boolean getJavaValue() {
		return value;
	}
}
