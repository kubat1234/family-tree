package tcs.familytree.core.relationtype;

public abstract class AbstractConnectionRelationType implements RelationType{
    int id;
    RelationType relationType;
    public AbstractConnectionRelationType(int id){
        this.id = id;
    }
    public AbstractConnectionRelationType(RelationType relationType){
        this.relationType = relationType;
        this.id = relationType.getId();
    }

    public abstract void load();
    void unload(){
        relationType = null;
    }
    public boolean isUnloaded(){
        return relationType == null;
    }
    @Override
    public int getId(){
        return id;
    }
    @Override
    public String getName(){
        if(isUnloaded()) load();
        return relationType.getName();
    }
    @Override
    public RelationType getSuper(){
        if(isUnloaded()) load();
        return relationType;
    }
}
