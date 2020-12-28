package se.iths.autofix.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SparepartNotFoundException extends WebApplicationException {
    public SparepartNotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}