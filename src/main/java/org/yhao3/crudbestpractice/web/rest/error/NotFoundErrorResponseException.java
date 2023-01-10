package org.yhao3.crudbestpractice.web.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

/**
 * The error response for {@code 400 NOT FOUND}.
 * @author yhao3
 */
public class NotFoundErrorResponseException extends ErrorResponseException {

    private static ProblemDetail createProblemDetail(String entityName) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), entityName + " not found.");
        problemDetail.setType(URI.create(ErrorConstants.PROBLEM_BASE_URL + "/search?q=why-entity-not-found"));
        return problemDetail;
    }

    public NotFoundErrorResponseException(String entityName) {
        super(HttpStatus.NOT_FOUND, createProblemDetail(entityName), null, "errorKey", new String[]{"not.found.QQ"});
    }
}
