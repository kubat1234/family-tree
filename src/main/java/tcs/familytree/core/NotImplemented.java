package tcs.familytree.core;

public class NotImplemented extends UnsupportedOperationException{
    public NotImplemented(){
        super();
    }
    public NotImplemented(String message){
        super(message);
    }
    public NotImplemented(Exception ex){
        super(ex);
    }
    public NotImplemented(String message, Exception ex){
        super(message, ex);
    }

}

