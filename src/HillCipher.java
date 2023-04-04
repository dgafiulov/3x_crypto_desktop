import java.util.Objects;

public class HillCipher {


    public byte[] encode(byte[] orig, String key) {

        byte[][] keyMatrix = keyToMatrix("");

        return new byte[]{};
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

    private int[] matrixMultiply(byte[] a, byte[][] b) {
        int[] c = new int[2];
        c[0] = (a[0] * b[0][0] + a[1] * b[1][0]);
        c[1] = (a[0] * b[0][1] + a[1] * b[1][1]);
        return c;
    }
}
