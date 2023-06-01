package Exceptions;

public class NegativeCashException extends Exception{
    public NegativeCashException(){}
    public NegativeCashException(String message){
        super(message);
    }
}
