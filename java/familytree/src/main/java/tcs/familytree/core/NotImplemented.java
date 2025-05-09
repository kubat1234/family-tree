package tcs.familytree.core;

public class NotImplemented extends UnsupportedOperationException{
    NotImplemented(){
        super();
    }
    NotImplemented(String message){
        super(message);
    }
    NotImplemented(Exception ex){
        super(ex);
    }
    NotImplemented(String message, Exception ex){
        super(message, ex);
    }

}

