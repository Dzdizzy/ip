package anby;

import anby.task.Task;

/**
 * Displays messages to the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________\n";
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
        System.out.println(BANNER + LINE + INTRO);
    }

    /**
     * Shows the goodbye message when Anby exits.
     */
    public void showGoodbye() {
        System.out.println(END);
    }

    /**
     * Shows all tasks in the task list.
     *
     * @param tasks task list to show
     */
    public void showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("lol you have no tasks!");
        } else {
            System.out.println(LINE + "finish these and then reward me with burgers:\n");
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }

        System.out.println(LINE);
    }

    /**
     * Shows that a task has been marked as done.
     *
     * @param task task that was marked
     */
    public void showMarked(Task task) {
        System.out.println(LINE + "ooo you're done with this! that'll be one burger please:\n" + task + "\n" + LINE);
    }

    /**
     * Shows that a task has been marked as not done.
     *
     * @param task task that was unmarked
     */
    public void showUnmarked(Task task) {
        System.out.println(LINE + "hey why didn't you do this already?\n" + task + "\n" + LINE);
    }

    /**
     * Shows that a task has been deleted.
     *
     * @param task task that was deleted
     */
    public void showDeleted(Task task) {
        System.out.println(LINE + "okay i've taken away this task for you:\n" + task + "\n" + LINE);
    }

    /**
     * Shows that a task has been added.
     *
     * @param task task that was added
     * @param taskCount number of tasks after adding the task
     */
    public void showAdded(Task task, int taskCount) {
        System.out.println(LINE + "okay, this is a new task: " + task
                + "\nyou've got " + taskCount + " task(s) waiting for you...\n" + LINE);
    }

    /**
     * Shows an error message.
     *
     * @param message error message to show
     */
    public void showError(String message) {
        System.out.println(LINE + message + "\n" + LINE);
    }
}
