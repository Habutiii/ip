package habot.exception;

/**
 * Exception thrown when a provided format is invalid.
 */
public class HaBotInvalidFormatException extends HaBotException {
    public HaBotInvalidFormatException(String typeName, String content) {
        super("Invalid " + typeName + " format:\n" + content + "\n");
    }
}
