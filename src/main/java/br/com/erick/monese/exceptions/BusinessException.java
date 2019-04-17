package br.com.erick.monese.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String message, final String... args) {
        super(String.format(message, (Object[]) args));
    }

}
