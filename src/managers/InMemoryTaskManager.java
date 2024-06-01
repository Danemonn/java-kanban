package managers;

import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import managers.HistoryManager;
import managers.Manager;
import managers.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int idCounter = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager inMemoryHistoryManager = Manager.getDefaultHistory();


    @Override
    public void createTask(Task task) {    // создание задачи
        task.setId(idCounter++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubTask(SubTask subTask) {    // создание подзадачи( и добавление эпика )
        subTask.setId(idCounter++);
        subTasks.put(subTask.getId(), subTask);
        int epicId = subTask.getEpicId();
        ArrayList<Integer> tasksList = epics.get(epicId).getSubTasks();
        tasksList.add(subTask.getEpicId());
        checkEpicStatus(epicId);
    }

    @Override
    public void createEpic(Epic epic) {    // создание эпика
        epic.setId(idCounter++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Task> getTasks() {  //получение задач
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {    //получение подзадач
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {    //получение эпика
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllTask() {    //удаление всех задач
        tasks.clear();
    }

    @Override
    public void removeAllSubTask() {    //удаление всех подзадач
        for (Epic epic : epics.values()) {
            epic.getSubTasks().clear();
            checkEpicStatus(epic.getId());
        }
        subTasks.clear();
    }

    @Override
    public void removeAllEpic() {     //удалеине всех эпиков
        subTasks.clear();
        epics.clear();
    }

    @Override
    public Task getTaskId(int id) {   //получение задачи по id
        if(tasks.get(id) != null){
            inMemoryHistoryManager.addHistory(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public SubTask getSubTaskId(int id) {  //получение подзадачи по id
        if(subTasks.get(id) != null){
            inMemoryHistoryManager.addHistory(subTasks.get(id));
        }
        return subTasks.get(id);
    }

    @Override
    public Epic getEpicId(int id) {  //получение эпика по id
        if(epics.get(id) != null){
            inMemoryHistoryManager.addHistory(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public void deleteTaskId(int id) {  //удаление задачи по id
        tasks.remove(id);
    }

    @Override
    public void deleteSubTaskId(int id) {   //удаление подзадачи по id
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            Integer epicId = subTask.getEpicId();

            Epic epic = epics.get(epicId);
            if (epic != null) {
                ArrayList<Integer> tasksList = epic.getSubTasks();
                tasksList.removeIf(taskId -> taskId.equals(id));
                subTasks.remove(id);
                checkEpicStatus(epicId);
            } else {
                System.out.println("Эпик с id " + epicId + " не найден.");
            }
        } else {
            System.out.println("Подзадача с id " + id + " не найдена.");
        }
    }

    @Override
    public void deleteEpicId(int id) {  //удаление эпика по id
        ArrayList<Integer> tasksList = epics.get(id).getSubTasks();
        for (Integer taskId : tasksList) {
            subTasks.remove(taskId);
        }
        epics.remove(id);
    }

    @Override
    public void updateTask(Task task) {   //обнолвение задачи
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask newSubTask) {    //обнолвение подзадачи
        subTasks.put(newSubTask.getId(), newSubTask);
        int epicId = subTasks.get(newSubTask.getId()).getEpicId();
        checkEpicStatus(epicId);
    }

    @Override
    public void updateEpic(Epic epic) {    //обнолвение эпика
        epics.put(epic.getId(), epic);
    }

    @Override
    public void checkEpicStatus(int id) {   //проверка статуса подзадач в эпике
        int counterDone = 0;
        int counterNew = 0;

        ArrayList<Integer> subTasksList = epics.get(id).getSubTasks();
        for (Integer taskId : subTasksList) {
            if (subTasks.containsKey(taskId)) {
                if (subTasks.get(taskId).getStatus().equals(Status.DONE)) {
                    counterDone++;
                } else if (subTasks.get(taskId).getStatus().equals(Status.NEW)) {
                    counterNew++;
                }
            }

        }

        if (subTasksList.size() == counterDone || subTasksList.isEmpty()) {
            epics.get(id).setStatus(Status.DONE);
        } else if (subTasksList.size() == counterNew) {
            epics.get(id).setStatus(Status.NEW);
        } else {
            epics.get(id).setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return Manager.getDefaultHistory().getHistory();
    }
}


