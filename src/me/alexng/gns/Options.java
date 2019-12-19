package me.alexng.gns;

public class Options {

    private boolean usingStandardLib = true;

    public boolean isUsingStandardLib() {
        return usingStandardLib;
    }

    public Options setUsingStandardLib(boolean usingStandardLib) {
        this.usingStandardLib = usingStandardLib;
        return this;
    }
}
