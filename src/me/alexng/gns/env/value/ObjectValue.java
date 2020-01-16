package me.alexng.gns.env.value;

import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.ClassToken;

public class ObjectValue extends RawObjectValue {

	private final ValueDescriptor<ObjectValue> DESCRIPTOR = new ValueDescriptor<ObjectValue>() {
		@Override
		public String getTypeString() {
			return "object";
		}

		@Override
		public ObjectValue castTo(Value value) throws RuntimeException {
			return (ObjectValue) value;
		}
	};

	public ObjectValue(int objectId, ClassToken classToken, Scope objectScope) {
		super(objectId, classToken, objectScope);
	}
	
	@Override
	public Scope getJavaValue() {
		// TODO: Not really sure what to return here.
		return (Scope) super.getJavaValue();
	}

	@Override
	public ValueDescriptor<ObjectValue> getValueDescriptor() {
		return DESCRIPTOR;
	}
}
