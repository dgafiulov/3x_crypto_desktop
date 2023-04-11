import java.util.Arrays;
import java.util.Objects;

public class HillCipher {


    public byte[] encode(byte[] orig, String key) {
        byte[][] keyMatrix = keyToMatrix(key);
        byte[] encoded = bytesEncode(orig, keyMatrix);
        return encoded;
    }

    private byte[][] keyToMatrix(String key) {
        if (Objects.equals(key, "")) {
            key = "key";
        }
        byte[] bytes = key.getBytes();
        byte[][] keyMatrix = new byte[2][2];
        keyMatrix[0][0] = bytes[0 % bytes.length];
        keyMatrix[0][1] = bytes[1 % bytes.length];
        keyMatrix[1][0] = bytes[2 % bytes.length];
        keyMatrix[1][1] = bytes[3 % bytes.length];
        return keyMatrix;
    }

    private byte[] matrixMultiply(byte[] a, byte[][] b) {
        byte[] c = new byte[2];
        c[0] = (byte) ((((a[0] * b[0][0] + a[1] * b[1][0]) + 128) % 256) - 128);
        c[1] = (byte) ((((a[0] * b[0][1] + a[1] * b[1][1]) + 128) % 256) - 128);
        return c;
    }

    private byte[] bytesEncode(byte[] bytes, byte[][] key) {
        byte[] multiplyRes;
        for (int i = 0; i < bytes.length; i = i + 2) {
            multiplyRes = matrixMultiply(new byte[]{bytes[i], bytes[i + 1]}, key);
            bytes[i] = multiplyRes[0];
            bytes[i + 1] = multiplyRes[1];
        }
        return bytes;
    }
}
