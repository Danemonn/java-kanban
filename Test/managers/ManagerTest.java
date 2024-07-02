package managers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagerTest {
    @Test
    public void taskManagerClassAlwaysReturnsInitializedManager() {
        TaskManager taskManager = new Manager().getTaskManager();
        Assertions.assertNotNull(taskManager);
    }
}