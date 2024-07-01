package test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import managers.Manager;
import managers.TaskManager;

public class ManagersTest {

    @Test
    public void taskManagerClassAlwaysReturnsInitializedManager() {
        TaskManager taskManager = new Manager().getTaskManager();
        Assertions.assertNotNull(taskManager);
    }
}
