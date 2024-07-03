package managers;

import tasks.Task;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedListCustom history = new LinkedListCustom();

    @Override
    public void add(Task task) {
        history.linkLast(task);
    }

    @Override
    public void remove(int id) {
        history.removeNode(history.getNode(id));
    }


    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }


    private static class LinkedListCustom {

        private static class Node<T> {
            private T task;
            private Node<T> next;
            private Node<T> prev;

            public Node(Node<T> prev, T task, Node<T> next) {
                this.prev = prev;
                this.task = task;
                this.next = next;
            }
        }

        final Map<Integer, Node<Task>> historyMap = new HashMap<>();

        private Node<Task> head;
        private Node<Task> tail;


        public void linkLast(Task task) {
            if (historyMap.containsKey(task.getId())) {
                historyMap.remove(task.getId());
            }
            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            historyMap.put(task.getId(), newNode);
        }


        private void removeNode(Node<Task> node) {
            if (node != null) {
                final Node<Task> prev = node.prev;
                final Node<Task> next = node.next;
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                    node.prev = null;
                }
                if (next == null) {
                    tail = prev;
                } else {
                    next.prev = prev;
                    node.next = null;
                }
                if (historyMap.containsKey(node.task.getId())) {
                    historyMap.remove(node.task.getId());
                }
            }
        }

        private Node getNode(int id) {
            return historyMap.get(id);
        }

        public List<Task> getTasks() {
            List<Task> newListTasks = new ArrayList<>();
            for (Node<Task> historyNode : historyMap.values()) {
                newListTasks.add(historyNode.task);
            }

            return newListTasks;
        }

    }

}
