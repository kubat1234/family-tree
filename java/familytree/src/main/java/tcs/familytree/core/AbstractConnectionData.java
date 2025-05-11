package tcs.familytree.core;

import tcs.familytree.services.database.DatabaseConnection;

public abstract class AbstractConnectionData<T extends Identifiable> {
    protected int id;
    protected T data;
    protected DatabaseConnection connection;

    public AbstractConnectionData(int id, DatabaseConnection connection){
        this.id = id;
        this.connection = connection;
    }

    public AbstractConnectionData(T data, DatabaseConnection connection){
        this.data = data;
        this.connection = connection;
        id = data.getId();
    }

    public void unload(){
        data = null;
    }

    public abstract void load();

    public boolean isUnloaded(){
        return data == null;
    }

    public boolean isLoaded(){
        return data != null;
    }

    public int getId(){
        return id;
    }
}
