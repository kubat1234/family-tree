package tcs.familytree.core;

public class SimpleConnectionDate extends AbstractConnectionDate{
    DatabaseConection connection;
    SimpleConnectionDate(int id, DatabaseConection connection){
        super(id);
        this.connection = connection;
    }
    SimpleConnectionDate(Date date, DatabaseConection connection){
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
