import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        HillCipher cipher = new HillCipher();
        cipher.testFunc();

        /*

        File orig = new File("source-for-test/origTxt.txt");
        FileInputStream inputStream = new FileInputStream(orig);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] data = bufferedInputStream.readAllBytes();

        byte[] encoded = cipher.encode(data, "dog");

        inputStream.close();
        bufferedInputStream.close();

        System.out.println(Arrays.toString(data));
        System.out.println(Arrays.toString(encoded)); */

        //double[] decoded = cipher.decode(encoded, "dog");


        //System.out.println(Arrays.toString(decoded));


        /*FileOutputStream out1 = new FileOutputStream("source-for-test/coded.encry");
        BufferedOutputStream bfw1 = new BufferedOutputStream(out1);

        for (byte i:encoded) {
            bfw1.write(i);
        }

        bfw1.close();
        out1.close();

        FileOutputStream out2 = new FileOutputStream("source-for-test/decodedPic.png");
        BufferedOutputStream bfw2 = new BufferedOutputStream(out2);

        byte[] decoded = cipher.decode(encoded, "dog", true);

        for (byte i:decoded) {
            bfw2.write(i);
        }

        bfw2.close();
        out1.close();

        */
    }
}