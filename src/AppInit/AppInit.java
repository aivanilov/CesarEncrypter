package AppInit;

import Encrypter.Text;

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
        do {
            chooseServiceMode();
            switch (ServiceMode.MODE.getMode()) {
                case 1, 2 -> encryptOrDecryptWithCryptoKey();
                case 3 -> decryptTextByBruteForce();
                case 0 -> System.exit(0);
            }
        } while (true);
    }

    private static void decryptTextByBruteForce() {
        // Выбрать исходный файл и указать ключ шифрования
        Path pathInitial = selectInitialPath();
        String text = fileToString(pathInitial);
        Text encryptedText = new Text(text);

        // Выбрать, куда положить результирующий файл
        Path pathTarget = selectResultingPath();

        // Расшифровать и создать файл
        encryptedText.determineCryptoKey();
        stringToFile(encryptedText.getTargetText(), pathTarget);
    }

    private static void encryptOrDecryptWithCryptoKey() {
            // Выбрать исходный файл и указать ключ шифрования
            int cryptoKey = selectCryptoKey();
            Path pathInitial = selectInitialPath();
            String text = fileToString(pathInitial);
            Text encryptedText = new Text(text, cryptoKey);

            // Выбрать, куда положить результирующий файл
            Path pathTarget = selectResultingPath();

            // Зашифровать или расшифровать и создать файл
            if (ServiceMode.MODE.getMode() == 1) stringToFile(encryptedText.encryptText(), pathTarget);
            else stringToFile(encryptedText.decryptText(), pathTarget);
    }

    private static void chooseServiceMode() {
        System.out.println("""
                Добро пожаловать в CesarEncrypter!
                Пожалуйста, выберите, что вы хотите сделать:
                1 - зашифровать текст по ключу\s
                2 - расшифровать текст по ключу\s
                3 - расшифровать текст брутфорсом\s
                4 - расшифровать текст по паттерну (в разработке) \s
                0 - закрыть приложение\s
                """);

        switch (Integer.parseInt(AppInit.scanner.nextLine())) {
            case 1 -> ServiceMode.MODE.setMode(1);
            case 2 -> ServiceMode.MODE.setMode(2);
            case 3 -> ServiceMode.MODE.setMode(3);
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

        System.out.println("Файл выбран успешно.");

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