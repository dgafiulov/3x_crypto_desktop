import java.util.ArrayList;
import java.util.Arrays;

public class PlayfairCipher {

    PlayfairMatrix matrix;


    private ArrayList<Byte> array_init(ArrayList<Byte> orig) {
        if (orig.size() % 2 != 0) {
            orig.add((byte) -128);
        }
        return orig;
    }

    private byte[] notEven(byte[] orig) {
        byte[] encoded = new byte[orig.length - 1];
        System.arraycopy(orig, 0, encoded, 0, orig.length - 1);
        return encoded;
    }

    public byte[] encode(Byte[] origInc, String key) {
        ArrayList<Byte> orig = new ArrayList<>(Arrays.asList(origInc));
        matrix = new PlayfairMatrix(key);
        matrix.createMatrix();
        orig = array_init(orig);
        return encodeOrig(orig);
    }

    public byte[] decode(byte[] encoded, String key, boolean wasEven) {
        matrix = new PlayfairMatrix(key);
        matrix.createMatrix();
        decodeOrig(encoded);
        if (!wasEven) {
            encoded = notEven(encoded);
        }
        return encoded;
    }

    private byte[] encodeOrig(ArrayList<Byte> orig) {
        byte[] encoded = new byte[orig.size()];
        byte[] firstByte;
        byte[] secondByte;
        byte temp;

        for (int i = 0; i < orig.size(); i += 2) {
            firstByte = findElInMatrixByName(orig.get(i));
            secondByte = findElInMatrixByName(orig.get(i + 1));
            if (firstByte[0] == secondByte[0]) {
                firstByte[1] = (byte) ((firstByte[1] + 1) % 16);
                secondByte[1] = (byte) ((secondByte[1] + 1) % 16);
            } else if (firstByte[1] == secondByte[1]) {
                firstByte[0] = (byte) ((firstByte[0] + 1) % 16);
                secondByte[0] = (byte) ((secondByte[0] + 1) % 16);
            } else {
                temp = firstByte[1];
                firstByte[1] = secondByte[1];
                secondByte[1] = temp;
            }
            encoded[i] = findElInMatrixByCoordinates(firstByte);
            encoded[i + 1] = findElInMatrixByCoordinates(secondByte);
        }
        return encoded;
    }

    private byte[] decodeOrig(byte[] bytes) {
        byte[] firstByte;
        byte[] secondByte;
        byte temp;

        for (int i = 0; i < bytes.length; i += 2) {
            firstByte = findElInMatrixByName(bytes[i]);
            secondByte = findElInMatrixByName(bytes[i + 1]);
            if (firstByte[0] == secondByte[0]) {
                firstByte[1] = (byte) ((16 + firstByte[1] - 1) % 16);
                secondByte[1] = (byte) ((16 + secondByte[1] - 1) % 16);
            } else if (firstByte[1] == secondByte[1]) {
                firstByte[0] = (byte) ((16 + firstByte[0] - 1) % 16);
                secondByte[0] = (byte) ((16 + secondByte[0] - 1) % 16);
            } else {
                temp = firstByte[1];
                firstByte[1] = secondByte[1];
                secondByte[1] = temp;
            }
            bytes[i] = findElInMatrixByCoordinates(firstByte);
            bytes[i + 1] = findElInMatrixByCoordinates(secondByte);
        }
        return bytes;
    }

    private byte[] findElInMatrixByName(byte desired) {
        for (byte i = 0; i < 16; i++) {
            for (byte j = 0; j < 16; j++) {
                if (matrix.getMatrix()[i][j] == desired) {
                    return new byte[]{i, j};
                }
            }
        }
        return new byte[]{(byte) (1 / 0)};
    }

    private byte findElInMatrixByCoordinates(byte[] cords) {
        return matrix.getMatrix()[cords[0]][cords[1]];
    }
}