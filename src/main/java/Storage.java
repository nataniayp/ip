import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class Storage {
    protected String filePath;
    protected File f;
    protected TaskList tl;

    public Storage(String filePath, TaskList tl) {
        this.filePath = filePath;
        this.f = new File(filePath);

        // handles the case whereby file/directory does not exist
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.tl = tl;
    }

    /**
     * Reads data/duke.txt and copies data into task list.
     *
     * @throws DukeException if the named file exists but is a directory rather than a regular file
     * or does not exist but cannot be created, or cannot be opened for any other reason.
     */
    public void readFile() throws DukeException {
        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String text = s.nextLine();
                Map<String, String> m = Parser.parseTextFromFile(text);
                tl.addTask(m.get("finalText"));
                if (m.get("status").equals("X")) {
                    tl.getTaskList().get(tl.getTaskList().size() - 1).setDone();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new DukeException("No file found.");
        }
    }

    /**
     * Copies local TaskList to the specific file.
     *
     * @throws DukeException if the named file exists but is a directory rather than a regular file
     * or does not exist but cannot be created, or cannot be opened for any other reason.
     */
    public void copyToFile() throws DukeException {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (int i = 0; i < tl.getTaskList().size(); i++) {
                fw.write(tl.getTaskList().get(i).toString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DukeException("There's something wrong with the file.");
        }
    }

}
