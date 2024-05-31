package managers;

import java.util.ArrayList;
import java.util.List;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public interface TaskManager {


    void createTask(Task task);  // создание задачи

    void createSubTask(SubTask subTask);    // создание подзадачи( и добавление эпика )

    void createEpic(Epic epic);    // создание эпика

    ArrayList<Task> getTasks();  //получение задач

    ArrayList<SubTask> getSubTasks();   //получение подзадач

    ArrayList<Epic> getEpics();    //получение эпика

    void removeAllTask();    //удаление всех задач

    void removeAllSubTask();    //удаление всех подзадач

    void removeAllEpic();     //удалеине всех эпиков

    Task getTaskId(int id);   //получение задачи по id

    SubTask getSubTaskId(int id);  //получение подзадачи по id

    Epic getEpicId(int id);  //получение эпика по id

    void deleteTaskId(int id);  //удаление задачи по id

    void deleteSubTaskId(int id);   //удаление подзадачи по id

    void deleteEpicId(int id);  //удаление эпика по id

    void updateTask(Task task);   //обнолвение задачи

    void updateSubTask(SubTask newSubTask);    //обнолвение подзадачи

    void updateEpic(Epic epic);    //обнолвение эпика

    void checkEpicStatus(int id);   //проверка статуса подзадач в эпике

    List<Task> getHistory();
}

