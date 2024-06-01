package managers;

import tasks.Task;
import managers.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int MAX_HISTORY = 10;
    private static final List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void addHistory(Task task) {
        if (history.size() >= MAX_HISTORY) {
            history.remove(0);
        }
        history.add(task);
    }
}
