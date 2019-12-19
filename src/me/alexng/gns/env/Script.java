package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.GNSException;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.lexer.Lexer;
import me.alexng.gns.tokens.BlockToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Script {

	private String source;
	private String file;
	private LinkedList<Token> tokens;

	private Script(String source, String file) {
		this.source = source;
		this.file = file;
	}

	public Script(String source) {
		// TODO: BEtter name than null.
		this(source, "<NULL>");
	}

	public Script(File sourceFile) throws IOException {
		this(FileUtil.readFile(sourceFile), sourceFile.getPath());
	}

	/**
	 * Parses {@link #source}. Must be run before {@link #run()}.
	 *
	 * @throws ParsingException thrown if {@link #source} is invalid.
	 */
	public void parse() throws GNSException {
		LinkedList<Token> tokens = Lexer.tokenize(source, file);
		Assembler.assemble(tokens);
		this.tokens = tokens;
	}

	/**
	 * Runs the script. {@link #parse()} must be called before this.
	 */
	public void run() throws GNSException {
		BlockToken globalBlock = new BlockToken(tokens, FileIndex.wrap(tokens));
		Scope globalScope = Scope.createGlobalScope();
		addBuiltInFunctions(globalScope);
		globalBlock.executeBlockWithScope(globalScope);
	}

	private void addBuiltInFunctions(Scope globalScope) {
		globalScope.addFunction(new NativeFunction("print") {
			@Override
			public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
				if (values.length != 1) {
					throw new RuntimeException(caller, "Invalid number of arguments. Expected: 1. Got: " + values.length);
				}
				Value value = values[0];
				String output = null;
				switch (value.getType()) {
					case NULL:
						output = "null";
						break;
					case BOOLEAN:
					case NUMBER:
						output = value.getValue().toString();
						break;
					case OBJECT:
						ObjectValue object = (ObjectValue) value;
						output = object.toString(); // TODO: Call a toString function in the object
						break;
				}
				System.out.println(output);
				return null;
			}
		});
	}

	public String getSource() {
		return source;
	}
}
