package habot.exception;

/**
 * Exception thrown when a command is not found or recognized.
 */
public class HaBotCommandNotFoundException extends HaBotException {

    /**
     * Constructs a HaBotCommandNotFoundException with a default error message.
     */
    public HaBotCommandNotFoundException() {
        super("Sorry, What are you trying to say? ( ˶°ㅁ°) !???\n"
                + "I don't understand that command.");
    }
}
