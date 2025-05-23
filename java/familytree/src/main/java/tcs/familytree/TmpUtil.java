package tcs.familytree;

import java.util.Random;

public class TmpUtil {
    static Random random = new Random();
    public static int rand(int bound) {
        return random.nextInt(bound);
    }

    public static String randDate(int nullPercentage) {
        if(rand(100) < nullPercentage) {
            return null;
        }
        return String.valueOf(rand(31) + 1) + "." + String.format("%02d", rand(12) + 1) + "."
                + String.valueOf(rand(100) + 1924);
    }
}
