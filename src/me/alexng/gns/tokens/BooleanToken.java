package me.alexng.gns.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.BooleanValue;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;

public class BooleanToken extends Token {

	private BooleanValue bool;

	// TODO: Think about merging this class and number class into a general constant value class.
	//  I can't think of many reasons why we wouldn't.

	public BooleanToken(boolean bool, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.bool = bool ? BooleanValue.TRUE : BooleanValue.FALSE; // Calculate it once
	}

    @Override
    public Value execute(Scope scope) throws RuntimeException {
        return bool;
    }

	@Override
	public String toString() {
		return "<BooleanToken " + bool.getValue().toString() + ">";
	}
}
