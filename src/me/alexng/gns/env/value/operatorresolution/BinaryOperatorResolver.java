package me.alexng.gns.env.value.operatorresolution;

import me.alexng.gns.env.value.Value;
import me.alexng.gns.env.value.ValueDescriptor;
import me.alexng.gns.tokens.operators.OperatorToken;

/**
 * Defines a BinaryResolver for a non-symmetrical binary operator.
 *
 * @param <T> the return type of the resolver.
 */
public abstract class BinaryOperatorResolver<T extends Value> extends OperatorResolver<T> {

	private ValueDescriptor<?> value1Type, value2Type;

	public BinaryOperatorResolver(Class<? extends OperatorToken> operator, ValueDescriptor<?> value1Type, ValueDescriptor<?> value2Type) {
		super(operator);
		this.value1Type = value1Type;
		this.value2Type = value2Type;
	}

	public abstract T resolveValues(Value value1, Value value2);

	@Override
	public T resolveValues(Value... values) {
		if (values.length != 2) {
			return null;
		}
		String typeString1 = values[0].getValueDescriptor().getTypeString();
		String typeString2 = values[1].getValueDescriptor().getTypeString();
		if (checkTypes(typeString1, typeString2)) {
			return resolveValues(values[0], values[1]);
		}
		return null;
	}

	/**
	 * @return type if the types match
	 */
	private boolean checkTypes(String type1, String type2) {
		return type1.equals(value1Type.getTypeString()) && type2.equals(value2Type.getTypeString());
	}
}
