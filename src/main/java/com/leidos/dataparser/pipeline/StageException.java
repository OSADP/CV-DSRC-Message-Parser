package com.leidos.dataparser.pipeline;

/**
 * Created by rushk1 on 4/3/2015.
 */
public class StageException extends Exception {

    private Class<? extends Stage> origin;

    public StageException(Class<? extends Stage> origin) {
        super();
        this.origin = origin;
    }

    public StageException(Class<? extends Stage> origin , String message) {
        super(message);
        this.origin = origin;
    }

    public Class<? extends Stage> getOrigin() {
        return origin;
    }

    @Override
    public String getMessage() {
        return "Error in stage " + origin.getName() + ": " + super.getMessage();
    }
}
