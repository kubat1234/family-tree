package tcs.familytree.core;

public interface ConnectionData {
    void load();
    void unload();
    boolean isLoaded();
    boolean isUnloaded();
}
