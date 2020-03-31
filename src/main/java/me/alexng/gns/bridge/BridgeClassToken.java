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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BridgeClassToken<T> extends ClassToken {

	private Class<T> bridgeClass;
	Map<String, Field> variables;
	List<Method> functions;

	public BridgeClassToken(Class<T> bridgeClass) throws ParsingException {
		super(new IdentifierToken(bridgeClass.getSimpleName(), FileIndex.INTERNAL_INDEX), null, FileIndex.INTERNAL_INDEX);
		this.bridgeClass = bridgeClass;
	}

	@Override
	public ObjectValue createInstance(ObjectConstructionToken caller, Value[] values, Scope callingScope) throws RuntimeException {
		Object bridgeInstance = createBridgeInstance(values);
		int objectId = callingScope.getEnvironment().incrementObjectId();
		Scope objectScope = Scope.createObjectScope(getIdentifier().getName(), callingScope.getGlobalScope());
		objectScope.variableProvider = new BridgeVariableProvider(bridgeInstance, variables, objectScope.variableProvider);
		objectScope.functionProvider = new BridgeFunctionProvider(bridgeInstance, functions, objectScope.functionProvider);
		ObjectValue objectValue = new ObjectValue(objectId, this, objectScope);
		return objectValue;
	}

	/**
	 * Creates a Java Object of the {@link #bridgeClass}.
	 *
	 * @param values constructor values
	 * @return the created object
	 */
	T createBridgeInstance(Value[] values) throws RuntimeException {
		Class<?>[] valueTypes = new Class<?>[values.length];
		for (int i = 0; i < values.length; i++) {
			valueTypes[i] = values[i].getClass();
		}
		try {
			Constructor<T> constructor = bridgeClass.getConstructor(valueTypes);
			if (values.length > 0 && !constructor.isAnnotationPresent(Expose.class)) {
				throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Constructor is not exposed.");
			}
			return constructor.newInstance((Object[]) values);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Failed to create bridge instance:" + e.getClass() + ": " + e.getMessage());
		}
	}

	public void setVariables(Map<String, Field> variables) {
		this.variables = variables;
	}

	public void setFunctions(LinkedList<Method> functions) {
		this.functions = functions;
	}

	public Class<?> getBridgeClass() {
		return bridgeClass;
	}
}
