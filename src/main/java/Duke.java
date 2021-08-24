import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a personalised chat bot for CS2103/T iP.
 */
public class Duke {
    private Storage storage;
    private TaskList tl;

    public Duke(String filePath) {
        tl = new TaskList();
        storage = new Storage(filePath, tl);
        try {
            storage.readFile();
        } catch (DukeException e) {
            TextUi.showErrorMessage(e.getMessage());
        }

    }

    public void run() {
        TextUi.showWelcomeMessage();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            try {
                String text = sc.nextLine();
                if (text.equals("q")) {
                    TextUi.showGoodbyeMessage();
                    break;
                } else if (text.equals("ls")) {
                    tl.printList();
                } else if (text.contains("done")) {
                    tl.markAsDone(text);
                    storage.copyToFile();
                } else if (text.contains("delete")) {
                    tl.deleteTask(text);
                    storage.copyToFile();
                } else {
                    tl.addTask(text);
                    storage.copyToFile();
                    TextUi.showTaskAdded(tl);
                    TextUi.showUpdatedNumberOfTasks(tl);
                }
            } catch (DukeException e) {
                TextUi.showErrorMessage(e.getMessage());
            }
        }
        sc.close();
    }

    /**
     * The main method of Nat's chat bot.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }


}
