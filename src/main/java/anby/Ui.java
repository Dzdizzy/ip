package anby;

import anby.task.Task;

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

    public void showGreeting() {
        System.out.println(BANNER + LINE + INTRO);
    }

    public void showGoodbye() {
        System.out.println(END);
    }

    public void showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println(LINE + "lol you have no tasks!\n");
        } else {
            System.out.println(LINE + "finish these and then reward me with burgers:\n");
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }

        System.out.println(LINE);
    }

    public void showMarked(Task task) {
        System.out.println(LINE + "ooo you're done with this! that'll be one burger please:\n" + task + "\n" + LINE);
    }

    public void showUnmarked(Task task) {
        System.out.println(LINE + "hey why didn't you do this already?\n" + task + "\n" + LINE);
    }

    public void showDeleted(Task task) {
        System.out.println(LINE + "okay i've taken away this task for you:\n" + task + "\n" + LINE);
    }

    public void showAdded(Task task, int taskCount) {
        System.out.println(LINE + "okay, this is a new task: " + task
                + "\nyou've got " + taskCount + " task(s) waiting for you...\n" + LINE);
    }

    public void showFindResults(TaskList matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println(LINE + "oopsie i cant find anything\n");
        } else {
            System.out.println(LINE + "heres what i found:\n");
        }

        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println((i + 1) + ". " + matchingTasks.get(i));
        }

        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println(LINE + message + "\n" + LINE);
    }
}
