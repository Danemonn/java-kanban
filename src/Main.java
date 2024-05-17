import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import managers.TaskManager;


public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        taskManager.createTask(task1);
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.createEpic(epic2);

        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи 1", 2);
        taskManager.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2", "Описание подзадачи 2", 2);
        taskManager.createSubTask(subTask2);

        Task task3 = new Task(task1.getId(), "Обновленная задача 1", "Обновление описания задачи 1", Status.IN_PROGRESS);
        taskManager.updateTask(task3);
        Epic epic3 = new Epic(epic1.getId(), "Обновленный эпик 1", "Обновление описания эпика 1", Status.IN_PROGRESS, epic1.getSubTasks());
        taskManager.updateEpic(epic3);
        SubTask subTask3 = new SubTask(subTask1.getId(), "Обновленная подзадача 1", "Обновление описания подзадачи 1", Status.IN_PROGRESS, subTask1.getEpicId());
        taskManager.updateSubTask(subTask3);

        taskManager.getTaskId(task1.getId());
        taskManager.getEpicId(epic1.getId());
        taskManager.getSubTaskId(subTask1.getId());

        taskManager.getTasks();
        taskManager.getEpics();
        taskManager.getSubTasks();

        taskManager.deleteTaskId(task1.getId());
        taskManager.deleteEpicId(epic1.getId());
        taskManager.deleteSubTaskId(subTask1.getId());

        taskManager.removeAllTask();
        taskManager.removeAllEpic();
        taskManager.removeAllSubTask();

    }
}
