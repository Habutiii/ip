package habot.exception;

/**
 * To handle habot application specific exception
 */
public class HaBotException extends RuntimeException {
    public HaBotException(String message) {
        super(message);
    }
}
