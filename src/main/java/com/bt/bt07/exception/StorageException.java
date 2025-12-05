package com.bt.bt07.exception;

public class StorageException extends RuntimeException { //
    private static final long serialVersionUID = 1L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}