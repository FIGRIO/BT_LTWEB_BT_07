package com.bt.bt07.exception;

public class StorageFileNotFoundException extends StorageException { // [cite: 320]
    private static final long serialVersionUID = 1L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}