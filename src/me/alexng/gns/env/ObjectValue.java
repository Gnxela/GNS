package me.alexng.gns.env;

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
        return classToken.getIdentifier().getName() + objectScope;
    }

	public ClassToken getObjectType() {
		return classToken;
	}
}
