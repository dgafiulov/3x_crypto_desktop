import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("3x crypto beta 0.9");
        System.out.println();
        Cipher cipher = new Cipher();
        Scanner scan = new Scanner(System.in);
        String answer;
        String pathFrom;
        String pathTo;
        String password;

        while (true) {
            System.out.println("Что вы хотите сделать?");
            System.out.println("Напишите цифру 1 для шифрования файла");
            System.out.println("Напишите цифру 2 для расшифровки файла");
            System.out.println("Напишите цифру 3 для завершения работы программы");
            System.out.print("Действие: ");

            answer = scan.next();

            if (Objects.equals(answer, "1")) {
                System.out.println("Напишите полный путь до файла, который вы хотите зашифровать: ");
                pathFrom = scan.next();
                System.out.println("Напишите полный путь до файла (включая название), куда вы хотите сохранить зашифрованный файл: ");
                pathTo = scan.next();
                System.out.println("Напишите пароль для файла: ");
                password = scan.next();
                cipher.encode(pathFrom, pathTo, password);
                System.out.println("Шифрование прошло успешно!");
                System.out.println();
            } else if (Objects.equals(answer, "2")) {
                System.out.println("Напишите полный путь до файла, который вы хотите расшифровать: ");
                pathFrom = scan.next();
                System.out.println("Напишите полный путь до файла (включая название), куда вы хотите сохранить расшифрованный файл: ");
                pathTo = scan.next();
                System.out.println("Напишите пароль от файла: ");
                password = scan.next();
                cipher.decode(pathFrom, pathTo, password);
                System.out.println("Расшифровка прошла успешно!");
                System.out.println();
            } else if (Objects.equals(answer, "3")) {
                System.out.println("Спасибо за использование 3x crypto!");
                break;
            } else {
                System.out.println("Вы ввели некорректное число");
            }

        }
    }
}