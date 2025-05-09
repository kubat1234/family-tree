package tcs.familytree.core.relationtype;

public interface RelationTypeBuilder {
    RelationType build();
    RelationTypeBuilder setId(int id);
    RelationTypeBuilder setName(String name);
    RelationTypeBuilder setSuper(RelationType superType);
}
