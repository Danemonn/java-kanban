import managers.Manager;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;

class SubTaskTest {


    @Test
    public void subTaskEqualToEachOther() {
        TaskManager taskManager = new Manager().getTaskManager();
        Epic epic1 = new Epic("Epic1", "Description1");

        taskManager.createEpic(epic1);

        SubTask subTask2 = new SubTask("Подзадача 2", "Описание подзадачи 2", 0);
        SubTask subTask3 = new SubTask("Подзадача 2", "Описание подзадачи 2", 0);

        Assertions.assertEquals(subTask2, subTask3);
    }
}