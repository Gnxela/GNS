package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;

public class NullToken extends Token {
    public NullToken(FileIndex fileIndex) {
        super(fileIndex);
    }

    @Override
    public Value execute(Scope scope) {
        return Value.NULL;
    }

    @Override
    public String toString() {
        return "<Null >";
    }
}
