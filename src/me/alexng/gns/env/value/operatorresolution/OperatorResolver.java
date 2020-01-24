package me.alexng.gns.env.value.operatorresolution;

import com.sun.istack.internal.Nullable;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.operators.OperatorToken;

/**
 * @param <T> the return type of the resolver.
 */
public abstract class OperatorResolver<T extends Value> {

	private Class<? extends OperatorToken> operator;

	public OperatorResolver(Class<? extends OperatorToken> operator) {
		this.operator = operator;
	}

	/**
	 * @param values
	 * @return the resolved value, or null if the values do not match types.
	 */
	@Nullable
	public abstract T resolveValues(Value... values);

	/**
	 * @return the {@link me.alexng.gns.tokens.operators.OperatorToken} for which this class can resolve values.
	 */
	public Class<? extends OperatorToken> getOperator() {
		return operator;
	}
}
