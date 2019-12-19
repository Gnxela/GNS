package me.alexng.gns.gen.constructors;

import me.alexng.gns.Keyword;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.KeywordToken;
import me.alexng.gns.tokens.NullToken;
import me.alexng.gns.tokens.Token;

import java.util.ListIterator;

public class NullConstantConstructor implements Constructor {

    @Override
    public boolean isLeftToRight() {
        return true;
    }

    @Override
    public boolean accepts(Token token) {
        return Keyword.NULL.matches(token);
    }

    @Override
    public void construct(ListIterator<Token> tokens) {
        KeywordToken keywordToken = (KeywordToken) tokens.next();
        tokens.remove();
        tokens.add(new NullToken(keywordToken.getFileIndex()));
    }

}