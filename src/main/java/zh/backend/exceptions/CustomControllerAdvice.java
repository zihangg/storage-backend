package zh.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import zh.backend.responses.CustomErrorResponse;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(LocationInsufficientVolumeException.class)
    public ResponseEntity<CustomErrorResponse> handleInsufficientVolume(LocationInsufficientVolumeException e) {
        e.printStackTrace();
        CustomErrorResponse err = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Insufficient volume in location."
        );

        return ResponseEntity.badRequest().body(err);
    };

    @ExceptionHandler(LocationDoesNotExistException.class)
    public ResponseEntity<CustomErrorResponse> handleLocationDoesNotExist(LocationDoesNotExistException e) {
        e.printStackTrace();
        CustomErrorResponse err = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Location does not exist."
        );

        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(BoxDoesNotExistException.class)
    public ResponseEntity<CustomErrorResponse> handleBoxDoesNotExist(BoxDoesNotExistException e) {
        e.printStackTrace();
        CustomErrorResponse err = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Box does not exist."
        );

        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(BatchDoesNotExistException.class)
    public ResponseEntity<CustomErrorResponse> handleBatchDoesNotExist(BatchDoesNotExistException e) {
        e.printStackTrace();
        CustomErrorResponse err = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Batch does not exist."
        );

        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(IncorrectFromLocationException.class)
    public ResponseEntity<CustomErrorResponse> handleIncorrectFromLocation(IncorrectFromLocationException e) {
        e.printStackTrace();
        CustomErrorResponse err = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Box ID provided does not exist within location provided."
        );

        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        e.printStackTrace();

        // handle unique_username
        CustomErrorResponse err = null;
        if (e.getMessage().contains("unique_username")) {
            err = new CustomErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Username already exists."
            );
        }

        return ResponseEntity.badRequest().body(err);
    }
}
