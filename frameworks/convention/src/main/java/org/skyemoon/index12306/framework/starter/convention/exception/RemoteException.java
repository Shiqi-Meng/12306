package org.skyemoon.index12306.framework.starter.convention.exception;

import org.skyemoon.index12306.framework.starter.convention.errorcode.BaseErrorCode;
import org.skyemoon.index12306.framework.starter.convention.errorcode.IErrorCode;

public class RemoteException extends AbstractException {

    public RemoteException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    public RemoteException(String message) {
        this(message, null, BaseErrorCode.REMOTE_ERROR);
    }

    public RemoteException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    @Override
    public String toString() {
        return "RemoteException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
