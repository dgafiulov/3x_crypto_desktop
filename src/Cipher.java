import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.SortedMap;

public class Cipher {

    FileInputStream inputStream;
    BufferedInputStream bufferedInputStream;
    FileOutputStream out1;
    BufferedOutputStream bfw1;

    VigenereCipher vigenereCipher = new VigenereCipher();
    PlayfairCipher playfairCipher = new PlayfairCipher();
    HillCipher hillCipher = new HillCipher();

    public void encode(String pathToOrig, String pathToDest, String key) throws IOException {
        byte[] bytes = read(pathToOrig);
        boolean even = bytes.length % 2 == 0;

        bytes = vigenereCipher.encode(bytes, key);
        bytes = hillCipher.encode(bytes, key);
        bytes = playfairCipher.encode(toObjByte(bytes), key);

        write(pathToDest, arrayAddition(new byte[]{even ? (byte) 0 : 1}, bytes));
    }

    public void decode(String pathToOrig, String pathToDest, String key) throws IOException {
        byte[] bytesOr = read(pathToOrig);
        byte[] bytes = Arrays.copyOfRange(bytesOr, 1, bytesOr.length);
        boolean even = (bytesOr[0] == 0);

        bytes = playfairCipher.decode(bytes, key);
        bytes = hillCipher.decode(bytes, key, even);
        bytes = vigenereCipher.decode(bytes, key);

        write(pathToDest, bytes);
    }

    private byte[] read(String path) throws IOException {
        inputStream = new FileInputStream(path);
        bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bytes = bufferedInputStream.readAllBytes();
        inputStream.close();
        bufferedInputStream.close();
        return bytes;
    }

    private void write(String path, byte[] bytes) throws IOException {
        out1 = new FileOutputStream(path);
        bfw1 = new BufferedOutputStream(out1);

        for (byte i:bytes) {
            bfw1.write(i);
        }

        bfw1.close();
        out1.close();
    }

    private Byte[] toObjByte(byte[] bytes) {
        Byte[] bytesObj = new Byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            bytesObj[i] = bytes[i];
        }

        return bytesObj;
    }

    private byte[] arrayAddition(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}