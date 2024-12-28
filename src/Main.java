import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            List<String> lines = Files.lines(Paths.get("source.txt"))
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .peek(Main::validateNumber)
                    .collect(Collectors.toList());

            if (lines.isEmpty()) {
                throw new IOException("Файл порожній або не містить валідних чисел.");
            }

            String longestSequence = findLongestSequence(lines);

            System.out.println("Найдовша послідовність: " + longestSequence);

        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static String findLongestSequence(List<String> lines) {
        String longestSequence = lines.get(0);
        StringBuilder currentSequence = new StringBuilder(lines.get(0));

        for (int i = 1; i < lines.size(); i++) {
            String prevLine = lines.get(i - 1);
            String nextLine = lines.get(i);

            if (prevLine.substring(prevLine.length() - 2).equals(nextLine.substring(0, 2))) {
                currentSequence.append(nextLine.substring(2));
            } else {
                if (currentSequence.length() > longestSequence.length()) {
                    longestSequence = currentSequence.toString();
                }
                currentSequence = new StringBuilder(nextLine);
            }
        }

        if (currentSequence.length() > longestSequence.length()) {
            longestSequence = currentSequence.toString();
        }

        return longestSequence;
    }

    private static void validateNumber(String number) {
        try {
            if (!number.matches("\\d+")) {
                throw new InvalidNumberException("Число \"" + number + "\" містить некоректні символи.");
            }
            if (number.length() < 4) {
                throw new InvalidNumberException("Число \"" + number + "\" має менше ніж 4 цифри.");
            }
        } catch (InvalidNumberException e) {
            throw new RuntimeException("Невалідний рядок: " + number + ". Причина: " + e.getMessage());
        }
    }
}