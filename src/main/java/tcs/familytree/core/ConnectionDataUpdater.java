package tcs.familytree.core;

public interface ConnectionDataUpdater {
    void update(Identifiable data);
    void register(ConnectionData<? extends Identifiable> data);
    void unregister(ConnectionData<? extends Identifiable> data);
}
