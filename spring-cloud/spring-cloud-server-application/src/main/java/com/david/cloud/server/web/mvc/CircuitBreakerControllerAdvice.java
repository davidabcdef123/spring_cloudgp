package com.david.cloud.server.web.mvc;

import com.david.cloud.server.controller.ServerController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

/**
 * Created by sc on 2019-03-11.
 */
@RestControllerAdvice(assignableTypes = ServerController.class)
public class CircuitBreakerControllerAdvice {

    @ExceptionHandler
    public void onTimeoutException(TimeoutException timeoutException,
                                   Writer writer) throws IOException {
        writer.write(errorContent(""));
        writer.flush();
        writer.close();
    }

    public String errorContent(String message) {
        return "CircuitBreakerControllerAdvice Fault";
    }
}
