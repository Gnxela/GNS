package me.alexng.gns.env.value;

import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.ClassToken;

public class ObjectValue extends Value {

	private ClassToken classToken;
	private Scope objectScope;

	public ObjectValue(ClassToken classToken, Scope objectScope) {
		super(Type.OBJECT);
		this.classToken = classToken;
		this.objectScope = objectScope;
	}

	@Override
	public Object getJavaValue() {
		// TODO: Not really sure what to return here.
		return objectScope;
	}

	public Scope getObjectScope() {
		return objectScope;
	}

	public ClassToken getObjectType() {
		return classToken;
	}
}
