package tcs.familytree.core.date;

import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseError;

public class SimpleConnectionDate extends AbstractConnectionDate {
    DatabaseConnection connection;
    public SimpleConnectionDate(int id, DatabaseConnection connection){
        super(id);
        this.connection = connection;
    }
    public SimpleConnectionDate(Date date, DatabaseConnection connection){
        super(date);
        this.connection = connection;
    }

    @Override
    void load() {
        if(isUnloaded()){
            if(!connection.checkIfDateExist(id)){
                throw new DatabaseError("Date with id: " + id + "cannot load from database: " + connection + ".");
            }
            date = connection.getDate(id);
        }
    }
}
