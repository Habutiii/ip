package habot.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple UI test double capturing messages without printing.
 */
public class FakeUi extends Ui {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void send(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getLastMessage() {
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }
}
