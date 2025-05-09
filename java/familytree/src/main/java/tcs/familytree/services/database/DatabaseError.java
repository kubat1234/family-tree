package tcs.familytree.services.database;

public class DatabaseError extends RuntimeException{
    public DatabaseError() {
        super();
    }
    public DatabaseError(String message) {
        super(message);
    }
    public DatabaseError(String message, Exception ex) {
        super(message, ex);
    }
    public DatabaseError(Exception ex) {
        super(ex);
    }
}
