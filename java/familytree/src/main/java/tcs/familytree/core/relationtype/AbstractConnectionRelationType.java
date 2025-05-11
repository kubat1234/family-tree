package tcs.familytree.core.relationtype;

import tcs.familytree.core.ConnectionData;

public abstract class AbstractConnectionRelationType implements RelationType, ConnectionData {
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
    public void unload(){
        relationType = null;
    }
    public boolean isUnloaded(){
        return relationType == null;
    }
    public boolean isLoaded(){
        return relationType != null;
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
