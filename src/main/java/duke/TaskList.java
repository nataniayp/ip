package duke;

import java.util.ArrayList;

import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.Todo;
import duke.ui.TextUi;

/**
 * The tasklist of Duke.
 */
public class TaskList {

    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * A constructor of a TaskList.
     */
    public TaskList() {}

    /**
     * Retrieves the ArrayList.
     *
     * @return The ArrayList of a TaskList.
     */
    public ArrayList<Task> getTaskList() {
        return tasks;
    }

    public int getLength() {
        return tasks.size();
    }


    /**
     * Adds a to-do task to the tasklist.
     *
     * @param type The type of task.
     * @param description The descripton of task.
     * @throws DukeException if task is not a to-do.
     */
    public void addTask(String type, String description) throws DukeException {
        if (type == "TODO") {
            tasks.add(new Todo(description));
        } else {
            throw new DukeException("This is not a todo type.");
        }
    }

    /**
     * Adds a deadline or an event to the tasklist.
     *
     * @param type The type of task.
     * @param description The description of task.
     * @param time The time of task.
     * @throws DukeException if task is not a deadline or an event.
     */
    public void addTask(String type, String description, String time) throws DukeException {
        switch (type) {
        case "DEADLINE": tasks.add(new Deadline(description, time));
            break;
        case "EVENT": tasks.add(new Event(description, time));
            break;
        default: throw new DukeException("Wrong format");
        }
    }

    /**
     * Deletes a Task from the list.
     *
     * @param index The index of task in the tasklist.
     * @return The deleted task.
     * @throws DukeException if invalid index or no index is given.
     */
    public Task deleteTask(int index) throws DukeException {
        try {
            Task t = tasks.get(index);
            tasks.remove(index);
            return t;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Indicate which task that you want to delete.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The number is out of range. "
                    + "Indicate the correct task number that you want to delete.");
        }
    }

    /**
     * Marks a Task as done.
     *
     * @param index The index of the task.
     * @return The task marked as done.
     * @throws DukeException if invalid index or no index is given.
     */
    public Task markAsDone(int index) throws DukeException {
      try {
        Task t = tasks.get(index);
        t.setDone();
        return t;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Indicate which task you want to mark as done.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The number is out of range. "
                    + "Indicate the correct task number that you want to mark as done.");
        }
    }

    /**
     * Finds Task with a specific text.
     * @param text Text to find in the task.
     * @throws DukeException
     */
    public String findTask(String text) throws DukeException {
        String s = "";
        try {
            text = text.substring(5);
            ArrayList<String> temp = new ArrayList<>();
            int len = tasks.size();
            for (int i = 0; i < len; i++) {
                String taskInString = tasks.get(i).toString();
                int index = taskInString.indexOf(text);
                String taskIndex = String.valueOf(i + 1);
                if (index != -1) {
                    temp.add(taskIndex + ". " + taskInString);
                }
            }
            if (temp.isEmpty()) {
                System.out.println("There are no matching tasks in your list.");
                s += "There are no matching tasks in your list.";
            } else {
                System.out.println("Here are the matching tasks in your list:");
                s += "Here are the matching tasks in your list:\n";
                for (int i = 0; i < temp.size(); i++) {
                    System.out.println(temp.get(i));
                    s += temp.get(i) + "\n";
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException("Use the format --find xx--");
        }
        return s;
    }

    /**
     * Prints the list of Tasks.
     */
    public String printList() {
        String s = "";
        int len = tasks.size();
        if (len == 0) {
            System.out.println("The list is empty!");
            s += "The list is empty!";
        } else {
            s += TextUi.showTaskList(tasks);
        }
        assert s != "" : "s should not be empty in both cases";
        return s;
    }
}
