package test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import org.junit.jupiter.api.Test;
import managers.Manager;
import managers.TaskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryHistoryManagerTest {

    @Test
    void addHistory() {
        TaskManager taskManager = new Manager().getTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1");
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");

        taskManager.createTask(task);
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask("Подзадача 1", "Описание подзадачи 1", 1);

        taskManager.createSubTask(subTask);
        assertEquals(0, taskManager.getHistory().size(), "History is not null");//История пустая
        taskManager.getTaskId(task.getId());
        assertEquals(1, taskManager.getHistory().size(), "History not null");//История не пустая
    }

    @Test
    void remove() {
        TaskManager taskManager = new Manager().getTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1");
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");

        taskManager.createTask(task);
        taskManager.createEpic(epic);

        assertNotNull(taskManager.getTaskId(task.getId()), "Таска пустая");

        assertEquals(1, taskManager.getHistory().size(), "История пустая");

        taskManager.removeAllTask();

        assertEquals(0, taskManager.getTasks().size(), "История не пустая");


    }

    @Test
    void getHistory() {
        TaskManager taskManager = new Manager().getTaskManager();
        Task task = new Task("Задача 1", "Описание задачи 1");
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");

        taskManager.createTask(task);
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask("Подзадача 1", "Описание подзадачи 1", 1);

        taskManager.createSubTask(subTask);

        taskManager.getTaskId(task.getId());
        taskManager.getEpicId(epic.getId());
        taskManager.getSubTaskId(subTask.getId());

        assertEquals(3, taskManager.getHistory().size(), "История пустая");

        taskManager.deleteTaskId(task.getId());

        assertEquals(2, taskManager.getHistory().size(), "История пустая");

    }
}

