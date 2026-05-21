package org.Linh0512.core.exception;

public class ResourceNotFoundException extends BusinessException{
    public ResourceNotFoundException(String message) {
        super("RESOURCE_NOT_FOUND_EXCEPTION", message);
    }
}
