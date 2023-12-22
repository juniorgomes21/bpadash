package br.com.bpadash.exceptions;


import br.com.bpadash.errorValidation.ErrorsFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorsFile>> handleMaxSizeException(MultipartException e) {
        List<ErrorsFile> errorsFiles = new ArrayList<>();
        errorsFiles.add(new ErrorsFile("SIZE LIMIT"));

        return ResponseEntity.badRequest().body(errorsFiles);
    }
}
