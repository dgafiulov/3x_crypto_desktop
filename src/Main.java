import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static InputStream inputStream;
    private static BufferedInputStream bufferedInputStream;
    static FileOutputStream fileOutputStream;
    static BufferedOutputStream bufferedOutputStream;
    private static boolean wasEven;
    static Cipher cipher = new Cipher();
    public static void main(String[] args) throws IOException {

        System.out.println("3x crypto beta 1.0");
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
                try {
                    System.out.println("Напишите полный путь до файла, который вы хотите зашифровать: ");
                    pathFrom = scan.next();
                    readerInit(pathFrom);
                    System.out.println("Напишите полный путь до файла (включая название), куда вы хотите сохранить зашифрованный файл: ");
                    pathTo = scan.next();
                    System.out.println("Напишите пароль для файла: ");
                    password = scan.next();
                    encodeAndWrite(password, pathTo);
                    System.out.println("Шифрование прошло успешно!");
                    System.out.println();
                    readerDestruct();
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Вы ввели некоррректные данные, попробуйте еще раз");
                    System.out.println();
                }
            } else if (Objects.equals(answer, "2")) {
                try {
                    System.out.println("Напишите полный путь до файла, который вы хотите расшифровать: ");
                    pathFrom = scan.next();
                    readerInit(pathFrom);
                    System.out.println("Напишите полный путь до файла (включая название), куда вы хотите сохранить расшифрованный файл: ");
                    pathTo = scan.next();
                    System.out.println("Напишите пароль от файла: ");
                    password = scan.next();
                    decodeAndWrite(password, pathTo);
                    System.out.println("Расшифровка прошла успешно!");
                    System.out.println();
                    readerDestruct();
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Вы ввели некоррректные данные, попробуйте еще раз");
                    System.out.println();
                }
            } else if (Objects.equals(answer, "3")) {
                System.out.println("Спасибо за использование 3x crypto!");
                break;
            } else {
                System.out.println("Вы ввели некорректное число");
            }

        }
    }

    private static void readerInit(String path) throws IOException {
        inputStream = new FileInputStream(path);
        bufferedInputStream = new BufferedInputStream(inputStream);
        wasEven = bufferedInputStream.available() % 2 == 0;
    }

    private static void readerDestruct() throws IOException {
        inputStream.close();
        bufferedInputStream.close();
    }

    private static byte[] readLastChunk(byte[] lastChunk, int bytesRead) {
        byte[] temp;

        if (wasEven) {
            temp = new byte[bytesRead];
            System.arraycopy(lastChunk, 0, temp, 0, bytesRead);
        } else {
            temp = new byte[bytesRead + 1];
            System.arraycopy(lastChunk, 0, temp, 0, bytesRead);
            temp[bytesRead] = 0;
        }
        return temp;
    }

    private static byte[] readLastChunkForDecoding(byte[] lastChunk, int bytesRead) {
        byte[] temp = new byte[bytesRead];
        System.arraycopy(lastChunk, 0, temp, 0, bytesRead);
        return temp;
    }

    private static void writerInit(String path) throws FileNotFoundException {
        fileOutputStream = new FileOutputStream(path);
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    }

    private static void writerDestruct() throws IOException {
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    private static void encodeAndWrite(String key, String path) throws IOException {
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        boolean isLastChunk;

        writerInit(path);

        writeInFile(new byte[]{(byte) (wasEven ? 0 : 1)});

        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            isLastChunk = bytesRead < bufferSize;
            if (isLastChunk) {
                buffer = cipher.encodeChunk(readLastChunk(buffer, bytesRead), key);
            } else {
                buffer = cipher.encodeChunk(buffer, key);
            }
            writeInFile(buffer);
        }
        writerDestruct();
    }

    private static void writeInFile(byte[] orig) throws IOException {
        for (byte i:orig) {
            bufferedOutputStream.write(i);
        }
    }

    private static void decodeAndWrite(String key, String path) throws IOException {
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        long totalBytesRead = 0;
        long totalFileLength = bufferedInputStream.available();
        boolean isLastChunk;

        writerInit(path);

        byte[] firstSymbol = new byte[1];
        bufferedInputStream.read(firstSymbol);
        boolean wasEven = firstSymbol[0] == 0;

        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            isLastChunk = totalFileLength - totalBytesRead <= bufferSize;
            if (isLastChunk) {
                buffer = cipher.decodeChunk(readLastChunkForDecoding(buffer, bytesRead), key);
                if (!wasEven) {
                    buffer = Arrays.copyOfRange(buffer, 0, buffer.length - 1);
                }
            } else {
                buffer = cipher.decodeChunk(buffer, key);
            }
            writeInFile(buffer);
            totalBytesRead += bytesRead;
        }

        writerDestruct();
    }
}