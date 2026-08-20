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

        Scanner scanner = new Scanner(System.in);

        System.out.println(greet);

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("Bye")){
                System.out.println(end);
                break;
            }

            System.out.println(input);
        }
    }
}
