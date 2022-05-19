package AppInit;

import Encrypter.EncryptedText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Scanner;

public class AppInit {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Выбрать режим работы программы
        chooseServiceMode();
        if (ServiceMode.MODE.getMode() == 0) System.exit(0);

        while (ServiceMode.MODE.getMode() != 0) {
            // Выбрать исходный файл и указать ключ шифрования
            int cryptoKey = selectCryptoKey();
            Path pathInitial = selectInitialPath();
            String text = fileToString(pathInitial);
            EncryptedText encryptedText = new EncryptedText(text, cryptoKey);

            // Выбрать, куда положить результирующий файл
            Path pathTarget = selectResultingPath();

            // Зашифровать или расшифровать и создать файл
            if (ServiceMode.MODE.getMode() == 1) stringToFile(encryptedText.getEncryptedText(), pathTarget);
            else stringToFile(encryptedText.getDecryptedText(), pathTarget);

            chooseServiceMode();
        }
    }

    private static void chooseServiceMode() {
        System.out.println("""
                Добро пожаловать в CesarEncrypter!
                Пожалуйста, выберите, что вы хотите сделать:
                1 - зашифровать текст\s
                2 - расшифровать текст\s
                иное - закрыть приложение
                """);

        switch (Integer.parseInt(AppInit.scanner.nextLine())) {
            case 1 -> ServiceMode.MODE.setMode(1);
            case 2 -> ServiceMode.MODE.setMode(2);
            default -> ServiceMode.MODE.setMode(0);
        }

        System.out.println("Выбран режим " + ServiceMode.MODE.getMode());

    }

    private static Path selectInitialPath() {
        if (ServiceMode.MODE.getMode() == 1)
            System.out.println("Пожалуйста, выберите файл, который желаете зашифровать:");
        else
            System.out.println("Пожалуйста, выберите файл, который желаете расшифровать:");

        String result = AppInit.scanner.nextLine();
        return Path.of(result);
    }

    private static int selectCryptoKey() {
        System.out.println("Укажите, на сколько символов сдвинуть алфавит (от 0 до 41): ");
        return Integer.parseInt(AppInit.scanner.nextLine());
    }

    private static Path selectResultingPath() {
        System.out.println("Укажите путь, куда опубликовать результат:");
        return Path.of(AppInit.scanner.nextLine());
    }

    private static String fileToString(Path path) {
        try (BufferedReader bufferedFile = new BufferedReader(new FileReader(path.toString()))) {
            StringBuilder builder = new StringBuilder();
            while (bufferedFile.ready()) {
                builder.append(bufferedFile.readLine());
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void stringToFile(String text, Path path) {
        try (BufferedWriter bufferedText = new BufferedWriter(new FileWriter(path.toString()))) {
            bufferedText.write(text);
        } catch (Exception e) {
            System.out.println("Ошибка. Файл не был сохранеён");
            throw new RuntimeException(e);
        }
        System.out.println("Файл успешно сохранён по адресу: " + path);
    }
}

// /Users/aivanilov/Desktop/JavaRushFiles/sourceText.txt
// /Users/aivanilov/Desktop/JavaRushFiles/newText.txt