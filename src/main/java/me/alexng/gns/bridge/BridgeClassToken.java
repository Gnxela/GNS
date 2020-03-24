package me.alexng.gns.bridge;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.ClassToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.ObjectConstructionToken;

import java.lang.reflect.Field;
import java.util.Map;

public class BridgeClassToken extends ClassToken {

	private Class<?> bridgeClass;
	private Map<String, Field> variables;

	public BridgeClassToken(Class<?> bridgeClass) throws ParsingException {
		super(new IdentifierToken(bridgeClass.getSimpleName(), FileIndex.INTERNAL_INDEX), null, FileIndex.INTERNAL_INDEX);
		this.bridgeClass = bridgeClass;
	}

	public void setVariables(Map<String, Field> variables) {
		this.variables = variables;
	}

	@Override
	public ObjectValue createInstance(ObjectConstructionToken caller, Value[] values, Scope callingScope) throws RuntimeException {
		Object bridgeInstance = createBridgeInstance();
		int objectId = callingScope.getEnvironment().incrementObjectId();
		Scope objectScope = Scope.createObjectScope(getIdentifier().getName(), callingScope.getGlobalScope());
		objectScope.variableProvider = new BridgeVariableProvider(bridgeInstance, variables);
		ObjectValue objectValue = new ObjectValue(objectId, this, objectScope);
		return objectValue;
	}

	private Object createBridgeInstance() throws RuntimeException {
		try {
			return bridgeClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Failed to create bridge instance:" + e.getClass() + ": " + e.getMessage());
		}
	}

	public Class<?> getBridgeClass() {
		return bridgeClass;
	}
}
