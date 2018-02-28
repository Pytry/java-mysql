package org.xitikit.examples.java.mysql.logwatch.data;

import javax.validation.ValidationException;

/**
 * Extends {@link ValidationException} to indicate there was a problem
 * specific to parsing a log. {@link ValidationException} was chosen
 * instead of IOException because this indicates an improperly formatted
 * entry in the log file, as opposed to a problem reading the input.
 */
public class LogEntryValidationException extends ValidationException{

    public LogEntryValidationException(final String message){

        super(message);
    }

    public LogEntryValidationException(){

    }

    public LogEntryValidationException(final String message, final Throwable cause){

        super(message, cause);
    }

    public LogEntryValidationException(final Throwable cause){

        super(cause);
    }
}
