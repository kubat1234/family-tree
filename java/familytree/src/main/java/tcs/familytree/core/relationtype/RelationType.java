package tcs.familytree.core.relationtype;

public interface RelationType {
    int getId();
    String getName();
    RelationType getSuper();
}
