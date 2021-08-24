import java.util.ArrayList;
import java.util.Map;

public class TaskList {
    private static ArrayList<Task> myList = new ArrayList<>();

    public TaskList() {}

    public ArrayList<Task> getTaskList() {
        return myList;
    }

    public int getLength() {
        return myList.size();
    }


    /**
     * Adds a Task to the list.
     *
     * @param text Command-line input.
     * @throws DukeException if format of input is wrong.
     */
    public void addTask(String text) throws DukeException {
        Map<String, String> m = Parser.parseTextFromInput(text);
        switch (m.get("type")) {
            case "T": myList.add(new Todo(m.get("description")));
                break;
            case "D": myList.add(new Deadline(m.get("description"), m.get("time")));
                break;
            case "E": myList.add(new Event(m.get("description"), m.get("time")));
        }
    }

    /**
     * Deletes a Task from the list.
     *
     * @param text Command-line input.
     * @throws DukeException if input does not include which Task to delete.
     */
    public void deleteTask(String text) throws DukeException {
        text = text.trim();
        try {
            int i = Integer.parseInt(text.split(" ")[1]) - 1;
            Task t = myList.get(i);
            TextUi.showTaskRemoved(t);
            myList.remove(i);
            TextUi.showUpdatedNumberOfTasks(this);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Indicate which task that you want to delete.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The number is out of range. Indicate the correct task number that you want to delete.");
        }
    }
    /**
     * Marks a Task as done.
     *
     * @param text Command-line input.
     * @throws DukeException if input does not include which Task to mark as done.
     */
    public static void markAsDone(String text) throws DukeException {
        try {
            int i = Integer.parseInt(text.split(" ")[1]) - 1;
            Task t = (Task) myList.get(i);
            t.setDone();
            TextUi.showTaskDone(t);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Indicate which task you want to mark as done.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The number is out of range. Indicate the correct task number that you want to mark as done.");
        }
    }

    /**
     * Prints the list of Tasks.
     */
    public static void printList() {
        int len = myList.size();
        if (len == 0) { System.out.println("The list is empty!"); }
        for (int i = 0; i < len; i++) {
            Task t = (Task) myList.get(i);
            TextUi.showTaskNumbered(i, t);
        }
    }
}
