package me.alexng.gns.env.value;

import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.ClassToken;

public class ObjectValue extends RawObjectValue {

	public ObjectValue(int objectId, ClassToken classToken, Scope objectScope) {
		super(objectId, classToken, objectScope);
	}
}
