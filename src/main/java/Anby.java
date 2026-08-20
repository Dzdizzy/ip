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
            String[] parts = input.split(" ");

            if (input.equalsIgnoreCase("list")) {
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

            if (parts[0].equalsIgnoreCase("mark")) {
                if (parts.length < 2 || !isValidTaskNumber(parts[1], count)) {
                    System.out.println(line + "hey give me a valid task number to mark!.\n" + line);
                    continue;
                }
                int id = Integer.parseInt(parts[1]) - 1;
                tasks[id].markAsDone();
                System.out.println(line + "ooo you're done with this! that'll be one burger please:\n" + tasks[id] + "\n" + line);
                continue;
            }

            if (parts[0].equalsIgnoreCase("unmark")) {
                if (parts.length < 2 || !isValidTaskNumber(parts[1], count)) {
                    System.out.println(line + "hey give me a valid task number to unmark!\n" + line);
                    continue;
                }
                int id = Integer.parseInt(parts[1]) - 1;
                tasks[id].unmarkAsDone();
                System.out.println(line + "hey why didn't you do this already?\n" + tasks[id] + "\n" + line);
                continue;
            }

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(end);
                break;
            }

            tasks[count] = new Task(input);
            count++;
            System.out.println(line + "added: " + input + "\n" + line);
        }
    }

    private static boolean isValidTaskNumber(String text, int taskCount) {
        try {
            int taskNumber = Integer.parseInt(text);
            return taskNumber >= 1 && taskNumber <= taskCount;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
