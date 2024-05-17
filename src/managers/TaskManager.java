package managers;

import java.util.ArrayList;
import java.util.HashMap;
import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class TaskManager {

    private int idCounter = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();


    public void createTask(Task task) {    // создание задачи
        task.setId(idCounter++);
        tasks.put(task.getId(), task);
    }


    public void createSubTask(SubTask subTask) {    // создание подзадачи( и добавление эпика )
        subTask.setId(idCounter++);
        subTasks.put(subTask.getId(), subTask);
        int epicId = subTask.getEpicId();
        ArrayList<Integer> tasksList = epics.get(epicId).getSubTasks();
        tasksList.add(subTask.getEpicId());
        checkEpicStatus(epicId);
    }


    public void createEpic(Epic epic) {    // создание эпика
        epic.setId(idCounter++);
        epics.put(epic.getId(), epic);
    }


    public ArrayList<Task> getTasks() {  //получение задач
        return new ArrayList<>(tasks.values());
    }


    public ArrayList<SubTask> getSubTasks() {    //получение подзадач
        return new ArrayList<>(subTasks.values());
    }


    public ArrayList<Epic> getEpics() {    //получение эпика
        return new ArrayList<>(epics.values());
    }


    public void removeAllTask() {    //удаление всех задач
        tasks.clear();
    }


    public void removeAllSubTask() {    //удаление всех подзадач
        for(Epic epic : epics.values()) {
            epic.getSubTasks().clear();
            checkEpicStatus(epic.getId());
        }
        subTasks.clear();
    }


    public void removeAllEpic() {     //удалеине всех эпиков
        for(Epic epic : epics.values()) {
            int epicId = epic.getId();
            ArrayList<Integer> tasksList = epics.get(epicId).getSubTasks();
            for(Integer taskId : tasksList) {
                tasks.remove(taskId);
                subTasks.remove(taskId);
            }

        }
        epics.clear();
    }



    public Task getTaskId(int id) {   //получение задачи по id
        return tasks.get(id);
    }



    public SubTask getSubTaskId(int id) {  //получение подзадачи по id
        return subTasks.get(id);
    }



    public Epic getEpicId(int id) {  //получение эпика по id
        return epics.get(id);
    }



    public void deleteTaskId(int id) {  //удаление задачи по id
        tasks.remove(id);
    }



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



    public void deleteEpicId(int id) {  //удаление эпика по id
        ArrayList<Integer> tasksList = epics.get(id).getSubTasks();
        for(Integer taskId : tasksList) {
            tasks.remove(taskId);
            subTasks.remove(taskId);
        }
        epics.remove(id);
    }



    public void updateTask(Task task) {   //обнолвение задачи
        tasks.put(task.getId(), task);
    }



    public void updateSubTask(SubTask newSubTask) {    //обнолвение подзадачи
        subTasks.put(newSubTask.getId(), newSubTask);
        int epicId = subTasks.get(newSubTask.getId()).getEpicId();
        checkEpicStatus(epicId);
    }



    public void updateEpic(Epic epic) {    //обнолвение эпика
        epics.put(epic.getId(), epic);
    }



    public void checkEpicStatus(int id) {   //проверка статуса подзадач в эпике
        int counterDone = 0;
        int counterNew = 0;

        ArrayList<Integer> subTasksList = epics.get(id).getSubTasks();
        for(Integer taskId : subTasksList) {
            if(subTasks.containsKey(taskId)){
                if(subTasks.get(taskId).getStatus().equals(Status.DONE)) {
                    counterDone++;
                }else if(subTasks.get(taskId).getStatus().equals(Status.NEW)) {
                    counterNew++;
                }
            }

        }

        if(subTasksList.size() == counterDone  || subTasksList.isEmpty()) {
            epics.get(id).setStatus(Status.DONE);
        }else if(subTasksList.size() == counterNew) {
            epics.get(id).setStatus(Status.NEW);
        }else {
            epics.get(id).setStatus(Status.IN_PROGRESS);
        }
    }
}

