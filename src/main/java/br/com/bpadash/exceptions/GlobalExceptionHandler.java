package br.com.bpadash.exceptions;

import br.com.bpadash.model.ExceptionErroLog;
import br.com.bpadash.repository.ExceptionErroLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionErroLogRepository exceptionErroLogRepository;

    @ExceptionHandler(Exception.class)
    public void  handleException(Exception e) {
        ExceptionErroLog erroLog = new ExceptionErroLog(e.getMessage(), getStackTraceAsString(e));
//        exceptionErroLogRepository.save(erroLog);
    }

    private String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}
