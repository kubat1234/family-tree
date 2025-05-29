package tcs.familytree.core;

public interface ConnectionData<T extends Identifiable> {
    int getId();
    void load();
    void load(T data);
    void unload();
    boolean isLoaded();
    boolean isUnloaded();
    Class<? extends Identifiable> getDataClass();
}
