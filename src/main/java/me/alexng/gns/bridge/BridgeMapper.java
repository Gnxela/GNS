package me.alexng.gns.bridge;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.tokens.value.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class BridgeMapper {

	private static final String BASE_TYPE_STRING = "me.alexng.gns.tokens.value.Value";

	public static <T> BridgeTemplateToken<T> mapBridge(Class<T> bridgeClass) throws ParsingException {
		// TODO: Allow for bridges to define a name different from class name
		BridgeTemplateToken<T> bridgeClassToken = new BridgeTemplateToken<T>(bridgeClass);
		bridgeVariables(bridgeClassToken);
		bridgeFunctions(bridgeClassToken);
		checkNames(bridgeClassToken);
		return bridgeClassToken;
	}

	private static void bridgeFunctions(BridgeTemplateToken<?> bridgeClassToken) throws ParsingException {
		LinkedList<Method> functions = new LinkedList<>();
		for (Method method : bridgeClassToken.getBridgeClass().getMethods()) {
			if (method.isAnnotationPresent(Expose.class)) {
				checkMethodTypes(method);
				functions.add(method);
			}
		}
		bridgeClassToken.functions = functions;
	}

	private static void bridgeVariables(BridgeTemplateToken<?> bridgeClassToken) throws ParsingException {
		Map<String, Field> fields = new HashMap<>();
		for (Field field : bridgeClassToken.getBridgeClass().getFields()) {
			if (field.isAnnotationPresent(Expose.class)) {
				checkField(field);
				fields.put(field.getName(), field);
			}
		}
		bridgeClassToken.variables = fields;
	}

	private static void checkNames(BridgeTemplateToken<?> bridgeClassToken) throws ParsingException {
		Set<String> names = new HashSet<>();
		for (String fieldName : bridgeClassToken.variables.keySet()) {
			boolean unique = names.add(fieldName);
			if (!unique) {
				throw new ParsingException(bridgeClassToken, "Field/Function name is not unique: '" + fieldName + "'");
			}
		}
		for (Method method : bridgeClassToken.functions) {
			boolean unique = names.add(method.getName());
			if (!unique) {
				throw new ParsingException(bridgeClassToken, "Field/Function name is not unique: '" + method.getName() + "'");
			}
		}
	}

	private static void checkMethodTypes(Method method) throws ParsingException {
		if (method.getReturnType() != Void.TYPE && !Value.class.isAssignableFrom(method.getReturnType())) {
			throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only methods with return type " + BASE_TYPE_STRING + " can be exposed: " + method.getName());
		}
		Class<?>[] types = method.getParameterTypes();
		for (int i = 0; i < types.length; i++) {
			Class<?> type = types[i];
			if (i == 0) {
				if (!Environment.class.isAssignableFrom(type)) {
					throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only methods with return type " + BASE_TYPE_STRING + " can be exposed: " + method.getName());
				}
			} else {
				if (!Value.class.isAssignableFrom(type)) {
					throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only methods with return type " + BASE_TYPE_STRING + " can be exposed: " + method.getName());
				}
			}
		}
	}

	private static void checkField(Field field) throws ParsingException {
		int modifiers = field.getModifiers();
		if (!Modifier.isPublic(modifiers) && !field.isAccessible()) {
			throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only accessible variables can be exposed: " + field.getName());
		}
		if (!Value.class.isAssignableFrom(field.getType())) {
			// TODO: Make it clear where this is being thrown.
			throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only variables with superclass " + BASE_TYPE_STRING + " can be exposed: " + field.getName());
		}
	}
}
