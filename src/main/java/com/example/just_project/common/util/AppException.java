package com.example.just_project.common.util;

/**
 * Для ответа кастомными сообщениями
 */
public class AppException extends RuntimeException {

    /**
     * @param message - string message thrown
     */
    public AppException(String message) {
        super(message);
    }

    /**
     * @param message   - string message thrown
     * @param throwable - {@link Throwable}
     */
    public AppException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
