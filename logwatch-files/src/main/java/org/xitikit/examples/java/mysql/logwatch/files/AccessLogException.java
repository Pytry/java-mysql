package org.xitikit.examples.java.mysql.logwatch.files;

/**
 * Indicates there was a problem specific to parsing the "access.log"
 * and saving the entries in a database.
 */
public class AccessLogException extends IllegalArgumentException{

    public AccessLogException(final String message){

        super(message);
    }

    public AccessLogException(){

    }

    public AccessLogException(final String message, final Throwable cause){

        super(message, cause);
    }

    public AccessLogException(final Throwable cause){

        super(cause);
    }
}
