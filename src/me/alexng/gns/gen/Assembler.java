package me.alexng.gns.gen;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.constructors.*;
import me.alexng.gns.tokens.AdditionToken;
import me.alexng.gns.tokens.AssignToken;
import me.alexng.gns.tokens.EqualToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.util.ExceptionUtil;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Takes tokenized input and assembles "secondary" tokens; tokens that are not directly parsed from the input text
 * but instead inferred from a sequence of tokens
 */
public class Assembler {

	private static final Constructor[] CONSTRUCTORS = new Constructor[]{
			// TODO: Some operations will need to be processed right to left.
			// TODO: Need an order in which binary operations are resolved.
			// TODO: Maybe combine ParametersConstructor, ExpressionConstructor and FunctionCallConstructor
			new BooleanConstantConstructor(),
			new BlockConstructor(),
			new ClassConstructor(),
			new ObjectConstructionConstructor(),
			new FunctionConstructor(),
			new FunctionCallConstructor(),
			new ExpressionConstructor(),
			new BinaryOperationConstructor(true, AdditionToken.class),
			new BinaryOperationConstructor(true, EqualToken.class),
			new BinaryOperationConstructor(false, AssignToken.class),
			new IfConstructor()
	};

	public static void assemble(LinkedList<Token> tokens) throws ParsingException {
		for (Constructor constructor : CONSTRUCTORS) {
			if (constructor.isLeftToRight()) {
				iterateForward(constructor, tokens);
			} else {
				iterateBackward(constructor, tokens);
			}
		}
	}

	private static void iterateForward(Constructor constructor, LinkedList<Token> tokens) throws ParsingException {
		ListIterator<Token> iterator = tokens.listIterator();
		while (iterator.hasNext()) {
			Token token = iterator.next();
			if (constructor.accepts(token)) {
				iterator.previous();
				constructor.construct(iterator);
			}
		}
	}

	private static void iterateBackward(Constructor constructor, LinkedList<Token> tokens) throws ParsingException {
		ListIterator<Token> iterator = tokens.listIterator(tokens.size() - 1);
		while (iterator.hasPrevious()) {
			Token token = iterator.previous();
			if (constructor.accepts(token)) {
				iterator.next();
				constructor.construct(iterator);
			}
		}
	}

	/**
	 * Collects all tokens in between two tokens, matching opening and closing tokens to each other.
	 *
	 * @return A list of tokens in between the matched tokens. Not including the open and close tokens (middle open and close tokens are included)
	 * @throws ParsingException Thrown when a matching end close token is not met.
	 */
	// TODO: Maybe make a util class.
	public static LinkedList<Token> matchTokens(ListIterator<Token> tokens, Token open, Token close) throws ParsingException {
		int depth = 1;
		LinkedList<Token> bucket = new LinkedList<>();
		Token first = tokens.next();
		if (!first.matches(open)) {
			throw ExceptionUtil.createParsingExpected("Invalid token", open, first);
		}
		tokens.remove();
		bucket.add(first);
		while (tokens.hasNext()) {
			Token token = tokens.next();
			bucket.add(token);
			tokens.remove();
			if (token.matches(open)) {
				depth++;
			} else if (token.matches(close)) {
				if (depth == 1) {
					return bucket;
				} else {
					depth--;
				}
			}
		}
		throw new ParsingException(first, "Matching token not found");
	}
}
