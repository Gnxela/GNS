package me.alexng.gns.env.scope;

public class NameProvider {

	private String name;

	public NameProvider(String name) {
		this.name = name;
	}

	public NameProvider extend(String extension, String separator) {
		return new NameProvider(name + (name.equals("") ? "" : separator) + extension);
	}

	public String getName() {
		return name;
	}
}
