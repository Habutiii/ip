package HaBot.Ui;

import HaBot.Exception.HaBotException;
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

    @Override
    public void listTasks(String taskListStr) throws HaBotException {
        String hint = "Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n";
        send(hint + taskListStr);
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getLastMessage() {
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }
}

