package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.env.Scope;

public class ObjectValue extends Value {

	private Scope objectScope;

	public ObjectValue(Scope objectScope, FileIndex fileIndex) {
		super(Type.OBJECT, fileIndex);
		this.objectScope = objectScope;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ObjectValue) {
			// TODO: Implement
			return false;
		}
		return false;
	}

	@Override
	public Object getJavaValue() {
		// TODO: Not really sure what to return here.
		return objectScope;
	}

	public Scope getObjectScope() {
		return objectScope;
	}
}
