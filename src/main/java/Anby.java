import java.util.Scanner;
import java.util.ArrayList;

public class Anby {
    public static void main(String[] args) {
        String banner = "    ___          __         \n"
                + "   /   |  ____  / /_  __  __\n"
                + "  / /| | / __ \\/ __ \\/ / / /\n"
                + " / ___ |/ / / / /_/ / /_/ / \n"
                + "/_/  |_/_/ /_/_.___/\\__, /  \n"
                + "                   /____/\n";

        String line = "____________________________________________________________\n";

        String intro = "Hey, I'm Anby\n"
                + "What do you need me for? I accept payment only in burgers\n";

        String end = "Alright see you. Don't forget my burgers okay";
        String greet = banner + line + intro;

        ArrayList<Task> tasks = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.println(greet);

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);

            try {
            if (input.equalsIgnoreCase("list")) { // list task
                if (tasks.isEmpty()) {
                    System.out.println("lol you have no tasks!");
                } else {
                    System.out.println(line + "finish these and then reward me with burgers:\n");
                }

                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                System.out.println(line);
                continue;
            }

            if (parts[0].equalsIgnoreCase("mark")) { // mark task
                if (parts.length < 2 || !isValidTaskNumber(parts[1], tasks.size())) {
                    throw new AnbyException("hey give me a valid task number to mark!");
                }
                int id = Integer.parseInt(parts[1]) - 1;
                Task currTask = tasks.get(id);
                if (currTask.isDone()) {
                    throw new AnbyException("you're actually already done with this task lol");
                }
                currTask.markAsDone();
                System.out.println(line + "ooo you're done with this! that'll be one burger please:\n" + currTask + "\n" + line);
                continue;
            }

            if (parts[0].equalsIgnoreCase("unmark")) { // unmark task
                if (parts.length < 2 || !isValidTaskNumber(parts[1], tasks.size())) {
                    throw new AnbyException("hey give me a valid task number to unmark!");
                }
                int id = Integer.parseInt(parts[1]) - 1;
                Task currTask = tasks.get(id);
                if (!currTask.isDone()) {
                    throw new AnbyException("bruh you haven't done this yet anyway");
                }
                currTask.unmarkAsDone();
                System.out.println(line + "hey why didn't you do this already?\n" + currTask + "\n" + line);
                continue;
            }

            if (parts[0].equalsIgnoreCase("delete")) {
                if (parts.length < 2 || !isValidTaskNumber(parts[1], tasks.size())) {
                    throw new AnbyException("hey give me a valid task number to delete!");
                }
                int id = Integer.parseInt(parts[1]) - 1;
                Task removedTask = tasks.get(id);
                tasks.remove(id);
                System.out.println(line + "okay i've taken away this task for you:\n" + removedTask + "\n" + line);
                continue;
            }

            if (input.equalsIgnoreCase("bye")) { // exit chatbot
                System.out.println(end);
                break;
            }

            if (parts[0].equalsIgnoreCase("todo")) { // create to do task
                if (parts.length == 1) {
                    throw new AnbyException("hey you forgot to put a todo haha");
                }
                else {
                    Todo newTodo = new Todo(parts[1]);
                    tasks.add(newTodo);
                    System.out.println(line + "okay, this is a new task: " + newTodo + "\nyou've got " + tasks.size() + " task(s) waiting for you...\n" + line);
                    continue;
                }
            }

            if (parts[0].equalsIgnoreCase("deadline")) { // create a deadline task
                if (parts.length == 1) {
                    throw new AnbyException("hey you forgot to put a deadline task haha");
                }
                else {
                    String[] parts1 = parts[1].split("/by", 2);
                    if (parts1.length != 2) {
                        throw new AnbyException("hey you forgot to put a deadline on the task\n(do deadline /by [time])");
                    }
                    else {
                        Deadline newDeadline = new Deadline(parts1[0].trim(), parts1[1].trim());
                        tasks.add(newDeadline);
                        System.out.println(line + "okay, this is a new task: " + newDeadline + "\nyou've got " + tasks.size() + " task(s) waiting for you...\n" + line);
                        continue;
                    }
                }
            }

            if (parts[0].equalsIgnoreCase("event")) { // create an event task
                if (parts.length == 1) {
                    throw new AnbyException("hey you forgot to put an event haha");
                }
                else {
                    String[] parts1 = parts[1].split("/from", 2);
                    if (parts1.length != 2) {
                        throw new AnbyException("hey you forgot to put a start time\n(do event /from [time] /to [time])");
                    }
                    else {
                        String[] parts2 =  parts1[1].split("/to", 2);
                        if (parts2.length != 2) {
                            throw new AnbyException("hey you forgot to put an end time\n(do event /from [time] /to [time])");
                        }
                        else {
                            Event newEvent = new Event(parts1[0].trim(), parts2[0].trim(), parts2[1].trim());
                            tasks.add(newEvent);
                            System.out.println(line + "okay, this is a new task: " + newEvent + "\nyou've got " + tasks.size() + " task(s) waiting for you...\n" + line);
                            continue;
                        }
                    }
                }
            }
            throw new AnbyException("what are ya tryna say?"); // unrecognised command / gibberish
            } catch (AnbyException e) {
                System.out.println(line + e.getMessage() + "\n" + line);
            }
        }
    }

    private static boolean isValidTaskNumber(String text, int taskCount) { // checks for valid number when marking/unmarking tasks
        try {
            int taskNumber = Integer.parseInt(text);
            return taskNumber >= 1 && taskNumber <= taskCount;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
