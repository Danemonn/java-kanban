package managers;

import managers.InMemoryTaskManager;
import managers.InMemoryHistoryManager;

public class Manager {

    public static TaskManager getTaskManager(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}

