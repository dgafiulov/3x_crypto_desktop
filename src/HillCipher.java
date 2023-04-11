import java.util.Arrays;
import java.util.Objects;

public class HillCipher {


    public byte[] encode(byte[] orig, String key) {
        byte[][] keyMatrix = keyToMatrix(key);
        byte[] encoded = bytesEncode(orig, keyMatrix);
        return encoded;
    }

    public byte[] decode(byte[] encoded, String key) {
        byte[][] keyMatrix = keyToMatrix(key);
        return new byte[]{};
    }

    private byte[][] keyToMatrix(String key) {
        if (Objects.equals(key, "")) {
            key = "key";
        }

        byte[] bytes;
        byte[][] keyMatrix = new byte[2][2];

        int amountOfMoves = 0;

        while (true) {
            bytes = key.getBytes();
            keyMatrix[0][0] = bytes[0 % bytes.length];
            keyMatrix[0][1] = bytes[1 % bytes.length];
            keyMatrix[1][0] = bytes[2 % bytes.length];
            keyMatrix[1][1] = bytes[3 % bytes.length];
            if (matrixDeterminant(keyMatrix) != 0) {
                break;
            } else if (amountOfMoves >= key.length()) {
                key = "donut";
            } else {
                key = key.substring(1) + key.charAt(0);
                amountOfMoves++;
            }
        }
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

    private int matrixDeterminant(byte[][] matrix) {
        return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
    }

    /*private byte[] inverseMatrix(byte[][] key) {

    }*/

    private byte[][] minorMatrix(byte[][] key) {
        byte temp1 = key[0][0];
        byte temp2 = key[0][1];

        key[0][0] = key[1][1];
        key[0][1] = key[1][0];
        key[1][0] = temp2;
        key[1][1] = temp1;

        return key;
    }

    private byte[][] matrixOfAlgebraicAdditions(byte[][] key) {
        key[0][1] *= -1;
        key[1][0] *= -1;

        return key;
    }
}
