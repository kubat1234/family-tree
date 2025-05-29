package tcs.familytree.core.relationtype;

import tcs.familytree.core.Identifiable;

public interface RelationType extends Identifiable {
    int getId();
    String getName();
    RelationType getSuper();
}
