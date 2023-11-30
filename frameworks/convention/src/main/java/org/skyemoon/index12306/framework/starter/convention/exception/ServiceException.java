package org.skyemoon.index12306.framework.starter.convention.exception;

import org.skyemoon.index12306.framework.starter.convention.errorcode.BaseErrorCode;
import org.skyemoon.index12306.framework.starter.convention.errorcode.IErrorCode;

public class ServiceException extends AbstractException {

    public ServiceException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    public ServiceException(String message) {
        this(message, null, BaseErrorCode.SERVICE_ERROR);
    }

    public ServiceException(IErrorCode errorCode) {
        this(null, errorCode);
    }

    public ServiceException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
