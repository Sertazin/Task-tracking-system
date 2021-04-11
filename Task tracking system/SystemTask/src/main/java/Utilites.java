import java.util.Scanner;

public class Utilites {
    public static String nextLine(Scanner scanner) {
        String line;
        while ((line = scanner.nextLine()).isEmpty()) {
        }
        return line;
    }
}
