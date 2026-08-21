import java.util.Scanner;

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

        Task[] tasks = new Task[100];
        int count = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println(greet);

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);

            try {
            if (input.equalsIgnoreCase("list")) { // list task
                if (count == 0) {
                    System.out.println("lol you have no tasks!");
                } else {
                    System.out.println(line + "finish these and then reward me with burgers:\n");
                }

                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(line);
                continue;
            }

            if (parts[0].equalsIgnoreCase("mark")) { // mark task
                if (parts.length < 2 || !isValidTaskNumber(parts[1], count)) {
                    throw new AnbyException("hey give me a valid task number to mark!");
                }
                int id = Integer.parseInt(parts[1]) - 1;
                if (tasks[id].isDone()) {
                    throw new AnbyException("you're actually already done with this task lol");
                }
                tasks[id].markAsDone();
                System.out.println(line + "ooo you're done with this! that'll be one burger please:\n" + tasks[id] + "\n" + line);
                continue;
            }

            if (parts[0].equalsIgnoreCase("unmark")) { // unmark task
                if (parts.length < 2 || !isValidTaskNumber(parts[1], count)) {
                    throw new AnbyException("hey give me a valid task number to unmark!");
                }
                int id = Integer.parseInt(parts[1]) - 1;
                if (!tasks[id].isDone()) {
                    throw new AnbyException("bruh you haven't done this yet anyway");
                }
                tasks[id].unmarkAsDone();
                System.out.println(line + "hey why didn't you do this already?\n" + tasks[id] + "\n" + line);
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
                    tasks[count] = new Todo(parts[1]);
                    System.out.println(line + "okay, this is a new task: " + tasks[count].toString() + "\nyou've got " + (count + 1) + " task(s) waiting for you...\n" + line);
                    count++;
                    continue;
                }
            }

            if (parts[0].equalsIgnoreCase("deadline")) { // create a deadline task
                if (parts.length == 1) {
                    System.out.println("hey you forgot to put a deadline task haha");
                    continue;
                }
                else {
                    String[] parts1 = parts[1].split("/by", 2);
                    if (parts1.length != 2) {
                        throw new AnbyException("hey you forgot to put a deadline on the task\n(do deadline /by [time])");
                    }
                    else {
                        tasks[count] = new Deadline(parts1[0].trim(), parts1[1].trim());
                        System.out.println(line + "okay, this is a new task: " + tasks[count].toString() + "\nyou've got " + (count + 1) + " task(s) waiting for you...\n" + line);
                        count++;
                        continue;
                    }
                }
            }

            if (parts[0].equalsIgnoreCase("event")) { // create an event task
                if (parts.length == 1) {
                    System.out.println("hey you forgot to put an event haha");
                    continue;
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
                            tasks[count] = new Event(parts1[0].trim(), parts2[0].trim(), parts2[1].trim());
                            System.out.println(line + "okay, this is a new task: " + tasks[count].toString() + "\nyou've got " + (count + 1) + " task(s) waiting for you...\n" + line);
                            count++;
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
