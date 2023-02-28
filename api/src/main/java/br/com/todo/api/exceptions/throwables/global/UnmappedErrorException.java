package br.com.todo.api.exceptions.throwables.global;

public class UnmappedErrorException extends Exception{
    public UnmappedErrorException(String message) {
        super(message);
    }

    public UnmappedErrorException(Exception ex) {
        super(ex);
    }
}
