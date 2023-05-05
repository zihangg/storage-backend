package zh.backend.exceptions;

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
}
