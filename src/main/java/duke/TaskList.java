package duke;

import java.util.ArrayList;
import java.util.Map;

import duke.parser.Parser;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.Todo;
import duke.ui.TextUi;


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
     * Adds a Task to the list.
     *
     * @param text Command-line input.
     * @throws DukeException if format of input is wrong.
     */
    public void addTask(String text) throws DukeException {
        try {
            Map<String, String> m = Parser.parseAddCommandFromInput(text);
            switch (m.get("type")) {
            case "T": tasks.add(new Todo(m.get("description")));
                break;
            case "D": tasks.add(new Deadline(m.get("description"), m.get("time")));
                break;
            case "E": tasks.add(new Event(m.get("description"), m.get("time")));
                break;
            default:
            }
        } catch (DukeException e) {
            throw e;
        }

    }

    /**
     * Deletes a Task from the list.
     *
     * @param text Command-line input.
     * @throws DukeException if input does not include which Task to delete.
     */
    public String deleteTask(String text) throws DukeException {
        String s = "";
        text = text.trim();
        try {
            int i = Integer.parseInt(text.split(" ")[1]) - 1;
            Task t = tasks.get(i);
            s += TextUi.showTaskRemoved(t);
            tasks.remove(i);
            s += TextUi.showUpdatedNumberOfTasks(this);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Indicate which task that you want to delete.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The number is out of range. "
                    + "Indicate the correct task number that you want to delete.");
        }
        return s;
    }
    /**
     * Marks a Task as done.
     *
     * @param text Command-line input.
     * @throws DukeException if input does not include which Task to mark as done.
     */
    public String markAsDone(String text) throws DukeException {
        String s = "";
        try {
            int i = Integer.parseInt(text.split(" ")[1]) - 1;
            Task t = (Task) tasks.get(i);
            t.setDone();
            s += TextUi.showTaskDone(t);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Indicate which task you want to mark as done.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The number is out of range. "
                    + "Indicate the correct task number that you want to mark as done.");
        }
        return s;
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
        }
        for (int i = 0; i < len; i++) {
            Task t = (Task) tasks.get(i);
            s += TextUi.showTaskNumbered(i, t);
        }
        return s;
    }
}
