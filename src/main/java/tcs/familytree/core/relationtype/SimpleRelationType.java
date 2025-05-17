package tcs.familytree.core.relationtype;

public class SimpleRelationType implements RelationType{
    int id;
    String name;
    RelationType superType;
    SimpleRelationType(int id, String name, RelationType superType){
        this.id = id;
        this.name = name;
        this.superType = superType;
    }
    SimpleRelationType(RelationType relationType){
        id = relationType.getId();
        name = relationType.getName();
        superType = relationType.getSuper();
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RelationType getSuper(){
        return superType;
    }
}
