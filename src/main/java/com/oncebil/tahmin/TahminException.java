package com.oncebil.tahmin;

/**
 * Created by erkinkarincaoglu on 28/05/2016.
 */
public class TahminException extends RuntimeException {

    public TahminException() {
    }

    public TahminException(String message) {
        super(message);
    }

    public TahminException(String message, Throwable cause) {
        super(message, cause);
    }

    public TahminException(Throwable cause) {
        super(cause);
    }

    public TahminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
