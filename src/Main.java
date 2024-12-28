import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "source.txt";
        try {
            List<String> numbers = Files.readAllLines(Paths.get(filePath));

            List<String> sequence = buildPuzzle(numbers);

            StringBuilder builder = new StringBuilder();
            sequence.forEach(builder::append);
            System.out.println("Побудована послідовність:");
            System.out.println(builder);
            System.out.println(builder.length());
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    public static List<String> buildPuzzle(List<String> numbers) {
        if (numbers.isEmpty()) return Collections.emptyList();

        List<String> result = new ArrayList<>();
        Set<String> used = new HashSet<>();

        String current = numbers.get(0);
        result.add(current);
        used.add(current);

        while (true) {
            String bestNext = null;
            int maxLength = -1;

            for (String next : numbers) {
                if (used.contains(next)) continue;

                String currentSuffix = current.substring(current.length() - 2);
                String nextPrefix = next.substring(0, 2);
                String nextSuffix = next.substring(next.length() - 2);

                if (currentSuffix.equals(nextPrefix) || currentSuffix.equals(nextSuffix)) {
                    int potentialLength = result.size() + 1;

                    if (potentialLength > maxLength) {
                        maxLength = potentialLength;
                        bestNext = next;
                    }
                }
            }

            if (bestNext != null) {
                result.add(bestNext);
                used.add(bestNext);
                current = bestNext;
            } else {
                break;
            }
        }

        return result;
    }
}
