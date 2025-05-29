package tcs.familytree.core;

import tcs.familytree.services.database.DatabaseConnection;

public abstract class AbstractConnectionData<T extends Identifiable> implements ConnectionData<T> {
    protected int id;
    protected T data;
    protected DatabaseConnection connection;

    public AbstractConnectionData(int id, DatabaseConnection connection){
        this.id = id;
        this.connection = connection;
        connection.getUpdater().register(this);
    }

    public AbstractConnectionData(T data, DatabaseConnection connection){
        this(data.getId(),connection);
        this.data = data;
    }

    public void unload(){
        data = null;
    }

    @Override
    public abstract void load();

    @Override
    public void load(T data){
        if(id != data.getId())throw new IllegalArgumentException("Data id is not equal to connection id");
        this.data = data;
    }

    @Override
    public boolean isUnloaded(){
        return data == null;
    }

    @Override
    public boolean isLoaded(){
        return data != null;
    }

    @Override
    public int getId(){
        return id;
    }
}
