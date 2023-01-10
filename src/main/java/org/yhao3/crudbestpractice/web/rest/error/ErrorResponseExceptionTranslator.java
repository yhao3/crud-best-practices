package org.yhao3.crudbestpractice.web.rest.error;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ErrorResponseExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ProblemDetail> handleNotFoundErrorResponseException(NotFoundErrorResponseException ex,
                                                                                 NativeWebRequest request) {

        return ResponseEntity.status(ex.getStatusCode()).headers(ex.getHeaders()).body(ex.getBody());
    }

    @ExceptionHandler
    protected ResponseEntity<ProblemDetail> handleElasticsearchException(ElasticsearchException ex,
                                                                         NativeWebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create(ErrorConstants.PROBLEM_BASE_URL + "/search?q=" + ex.getMessage().replaceAll(" ", "-")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
