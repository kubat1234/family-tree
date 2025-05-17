package tcs.familytree.core.relationtype;

public class SimpleRelationTypeBuilder implements RelationTypeBuilder{
    int id;
    String name;
    RelationType superType;
    @Override
    public RelationType build() {
        return new SimpleRelationType(id, name, superType);
    }
    @Override
    public RelationTypeBuilder setId(int id) {
        this.id = id;
        return this;
    }
    @Override
    public RelationTypeBuilder setName(String name) {
        this.name = name;
        return this;
    }
    @Override
    public RelationTypeBuilder setSuper(RelationType superType) {
        this.superType = superType;
        return this;
    }
}
