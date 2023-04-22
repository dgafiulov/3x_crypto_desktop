import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        Cipher cipher = new Cipher();

        cipher.encode("source-for-test/origVid.mp4", "source-for-test/coded.encry", "dog");
        cipher.decode("source-for-test/coded.encry", "source-for-test/origVid.mp4", "dog");
    }
}