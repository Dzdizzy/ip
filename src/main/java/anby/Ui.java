package anby;

import anby.task.Task;

/**
 * Displays messages to the user.
 */
public class Ui {
    private static final String BANNER = "    ___          __         \n"
            + "   /   |  ____  / /_  __  __\n"
            + "  / /| | / __ \\/ __ \\/ / / /\n"
            + " / ___ |/ / / / /_/ / /_/ / \n"
            + "/_/  |_/_/ /_/_.___/\\__, /  \n"
            + "                   /____/\n";
    private static final String INTRO = "Hey, I'm Anby\n"
            + "What do you need me for? I accept payment only in burgers\n";
    private static final String END = "Alright see you. Don't forget my burgers okay";

    /**
     * Creates a UI helper.
     */
    public Ui() {
    }

    /**
     * Shows the greeting message when Anby starts.
     */
    public void showGreeting() {
        System.out.println(getGreeting());
    }

    /**
     * Shows the goodbye message when Anby exits.
     */
    public void showGoodbye() {
        System.out.println(getGoodbye());
    }

    /**
     * Shows all tasks in the task list.
     *
     * @param tasks task list to show
     */
    public void showList(TaskList tasks) {
        System.out.println(getList(tasks));
    }

    /**
     * Returns the greeting message.
     *
     * @return greeting message
     */
    public String getGreeting() {
        return BANNER + INTRO;
    }

    /**
     * Returns the goodbye message.
     *
     * @return goodbye message
     */
    public String getGoodbye() {
        return END;
    }

    /**
     * Returns all tasks in the task list as a message.
     *
     * @param tasks task list to show
     * @return task list message
     */
    public String getList(TaskList tasks) {
        String message = formatTaskList(
                tasks,
                "lol you have no tasks!\n",
                "finish these and then reward me with burgers:\n");

        if (tasks.isEmpty()) {
            return message;
        }

        return message + "\n" + getTaskCountReaction(tasks.size()) + "\n";
    }

    /**
     * Shows that a task has been marked as done.
     *
     * @param task task that was marked
     */
    public void showMarked(Task task) {
        System.out.println(getMarked(task));
    }

    /**
     * Shows that a task has been marked as not done.
     *
     * @param task task that was unmarked
     */
    public void showUnmarked(Task task) {
        System.out.println(getUnmarked(task));
    }

    /**
     * Shows that a task has been deleted.
     *
     * @param task task that was deleted
     */
    public void showDeleted(Task task) {
        System.out.println(getDeleted(task));
    }

    /**
     * Shows that a task has been added.
     *
     * @param task task that was added
     * @param taskCount number of tasks after adding the task
     */
    public void showAdded(Task task, int taskCount) {
        System.out.println(getAdded(task, taskCount));
    }

    /**
     * Shows all tasks in the task list that matches with the searched keyword.
     *
     * @param matchingTasks list of tasks that match the searched keyword.
     */
    public void showFindResults(TaskList matchingTasks) {
        System.out.println(getFindResults(matchingTasks));
    }

    /**
     * Returns a message for a marked task.
     *
     * @param task task that was marked
     * @return marked task message
     */
    public String getMarked(Task task) {
        return "ooo you're done with this! that'll be one burger please:\n" + task;
    }

    /**
     * Returns a message for an unmarked task.
     *
     * @param task task that was unmarked
     * @return unmarked task message
     */
    public String getUnmarked(Task task) {
        return "hey why didn't you do this already?\n" + task;
    }

    /**
     * Returns a message for a deleted task.
     *
     * @param task task that was deleted
     * @return deleted task message
     */
    public String getDeleted(Task task) {
        return "okay i've taken away this task for you:\n" + task;
    }

    /**
     * Returns a message for an added task.
     *
     * @param task task that was added
     * @param taskCount number of tasks after adding the task
     * @return added task message
     */
    public String getAdded(Task task, int taskCount) {
        return "okay, this is a new task: \n" + task
                + "\nyou've got " + taskCount + " task(s) waiting for you..."
                + "\n\n" + getTaskCountReaction(taskCount);
    }

    /**
     * Returns all matching tasks as a message.
     *
     * @param matchingTasks list of tasks that match the searched keyword.
     * @return find results message
     */
    public String getFindResults(TaskList matchingTasks) {
        return formatTaskList(
                matchingTasks,
                "i dug up all the nearest burger chains and found nothing oopsie\n try a smaller keyword maybe",
                "heres what i found:\n");
    }

    private String formatTaskList(TaskList tasks, String emptyMessage, String headerMessage) {
        StringBuilder message = new StringBuilder();

        if (tasks.isEmpty()) {
            message.append(emptyMessage);
        } else {
            message.append(headerMessage);
        }

        for (int i = 0; i < tasks.size(); i++) {
            message.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }

        return message.toString();
    }

    private String getTaskCountReaction(int taskCount) {
        if (taskCount == 1) {
            return "just one item, light snack.";
        } else if (taskCount <= 3) {
            return "manageable combo meal, chef.";
        } else if (taskCount <= 5) {
            return "okay now the tray is getting heavy.";
        } else {
            return "bro this is a full family feast.";
        }
    }

    /**
     * Shows an error message.
     *
     * @param message error message to show
     */
    public void showError(String message) {
        System.out.println(getError(message));
    }

    /**
     * Returns an error message.
     *
     * @param message error message to show
     * @return error message
     */
    public String getError(String message) {
        return message;
    }
}
