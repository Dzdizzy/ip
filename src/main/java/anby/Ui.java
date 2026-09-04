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
        StringBuilder message = new StringBuilder();

        if (tasks.isEmpty()) {
            message.append("lol you have no tasks!\n");
        } else {
            message.append("finish these and then reward me with burgers:\n");
        }

        for (int i = 0; i < tasks.size(); i++) {
            message.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }

        return message.toString();
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
                + "\nyou've got " + taskCount + " task(s) waiting for you...";
    }

    /**
     * Returns all matching tasks as a message.
     *
     * @param matchingTasks list of tasks that match the searched keyword.
     * @return find results message
     */
    public String getFindResults(TaskList matchingTasks) {
        StringBuilder message = new StringBuilder();

        if (matchingTasks.isEmpty()) {
            message.append("oopsie i cant find anything\n");
        } else {
            message.append("heres what i found:\n");
        }

        for (int i = 0; i < matchingTasks.size(); i++) {
            message.append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
        }

        return message.toString();
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
