package me.alexng.gns.util;

import me.alexng.gns.ParsingException;
import me.alexng.gns.tokens.Token;

public class ExceptionUtil {

    public static ParsingException createParsingExpected(String message, Token expected, Token got) {
        return new ParsingException(got, message + ". Expected: " + expected.toString() + ". Received: " + got.toString());
    }

    public static ParsingException createParsingExpected(String message, Class<?> expected, Token got) {
        return new ParsingException(got, message + ". Expected: " + expected.getSimpleName() + ". Received: " + got.toString());
    }
}
