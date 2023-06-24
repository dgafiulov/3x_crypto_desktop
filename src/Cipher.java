import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.SortedMap;

public class Cipher {

    VigenereCipher vigenereCipher = new VigenereCipher();
    PlayfairCipher playfairCipher = new PlayfairCipher();
    HillCipher hillCipher = new HillCipher();

    public byte[] encodeChunk(byte[] bytes, String key) {
        try {
            bytes = vigenereCipher.encode(bytes, key);
            bytes = hillCipher.encode(bytes, key);
            bytes = playfairCipher.encode(toObjByte(bytes), key);
        } catch (Exception e) {
            System.out.println(e);
        }
        return bytes;
    }

    public byte[] decodeChunk(byte[] bytes, String key) {
        try {
            bytes = playfairCipher.decode(bytes, key);
            bytes = hillCipher.decode(bytes, key);
            bytes = vigenereCipher.decode(bytes, key);
        } catch (Exception e) {
            System.out.println(e);
        }
        return bytes;
    }

    private Byte[] toObjByte(byte[] bytes) {
        Byte[] bytesObj = new Byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            bytesObj[i] = bytes[i];
        }

        return bytesObj;
    }
}