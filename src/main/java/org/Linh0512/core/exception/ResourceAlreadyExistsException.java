package org.Linh0512.core.exception;

public class ResourceAlreadyExistsException extends BusinessException{
    public ResourceAlreadyExistsException(String message) {
        super("RESOURCE_ALREADY_EXISTS", message);
    }
}
