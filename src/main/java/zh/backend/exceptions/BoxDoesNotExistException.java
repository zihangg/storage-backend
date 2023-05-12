package zh.backend.exceptions;

import lombok.experimental.StandardException;

import java.util.NoSuchElementException;

@StandardException
public class BoxDoesNotExistException extends NoSuchElementException {
}
