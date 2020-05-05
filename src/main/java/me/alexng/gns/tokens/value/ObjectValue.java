package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.TemplateToken;

public class ObjectValue extends Value {

	private TemplateToken templateToken;
	private Scope objectScope;

	public ObjectValue(TemplateToken templateToken, Scope objectScope, FileIndex fileIndex) {
		super(Type.OBJECT, fileIndex);
		this.templateToken = templateToken;
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

	public TemplateToken getObjectType() {
		return templateToken;
	}
}
