import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    public int size() {
        return this.tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public Task getTask(String taskNumber) throws AnbyException {
        int index = parseTaskIndex(taskNumber);
        return this.tasks.get(index);
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public Task delete(String taskNumber) throws AnbyException {
        int index = parseTaskIndex(taskNumber);
        return this.tasks.remove(index);
    }

    public void mark(String taskNumber) throws AnbyException {
        Task task = getTask(taskNumber);

        if (task.isDone()) {
            throw new AnbyException("you're actually already done with this task lol");
        }

        task.markAsDone();
    }

    public void unmark(String taskNumber) throws AnbyException {
        Task task = getTask(taskNumber);

        if (!task.isDone()) {
            throw new AnbyException("bruh you haven't done this yet anyway");
        }

        task.unmarkAsDone();
    }

    private int parseTaskIndex(String text) throws AnbyException {
        try {
            int taskNumber = Integer.parseInt(text);

            if (taskNumber < 1 || taskNumber > this.tasks.size()) {
                throw new AnbyException("hey give me a valid task number!");
            }

            return taskNumber - 1;
        } catch (NumberFormatException e) {
            throw new AnbyException("hey give me a valid task number!");
        }
    }
}
