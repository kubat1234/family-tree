package tcs.familytree.core;

public interface Data {

    Object get();
    void set(Object newData);
    Data copy();
}
