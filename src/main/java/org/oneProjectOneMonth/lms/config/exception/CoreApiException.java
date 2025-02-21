package org.oneProjectOneMonth.lms.config.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.Map;

@Getter
public class CoreApiException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    private final Map<String, String> details;

    public CoreApiException(String message, Map<String, String> details) {
        super(message);
        this.details = details;
    }

    public CoreApiException(String message) {
        super(message);
        this.details = null;
    }

}
