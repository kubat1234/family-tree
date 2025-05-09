package tcs.familytree.core;

import java.io.IOException;
import java.nio.file.FileSystemException;

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
